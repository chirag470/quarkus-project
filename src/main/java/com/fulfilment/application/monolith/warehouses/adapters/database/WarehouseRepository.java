package com.fulfilment.application.monolith.warehouses.adapters.database;

//import com.fulfilment.application.monolith.warehouses.domain.models.Warehouse;
import com.warehouse.api.beans.Warehouse;
import com.fulfilment.application.monolith.warehouses.domain.ports.WarehouseStore;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import java.util.List;

@ApplicationScoped
public class WarehouseRepository implements WarehouseStore, PanacheRepository<DbWarehouse> {

  @Override
  public List<Warehouse> getAll() {
    return this.listAll().stream()
            .filter(dbWarehouse -> !dbWarehouse.isArchived)
            .map(DbWarehouse::toWarehouse)
            .toList();
  }

  @Override
  @Transactional
  public Warehouse create(Warehouse warehouse) {
    DbWarehouse entity = toEntity(warehouse);

    persist(entity);
    return entity.toWarehouse();
  }

  @Override
  @Transactional
  public void update(Warehouse warehouse) {
    DbWarehouse entity = find("businessUnitCode = ?1 and isArchived = false", warehouse.getBusinessUnitCode()).firstResult();
    if (entity == null) {
      throw new NotFoundException("Active warehouse not found with business unit code: " + warehouse.getBusinessUnitCode());
    }

    toEntity(warehouse, entity);
  }

  @Override
  @Transactional
  public void remove(Warehouse warehouse) {
    DbWarehouse entity = find("businessUnitCode = ?1 and isArchived = false", warehouse.getBusinessUnitCode()).firstResult();
    if (entity == null) {
      throw new NotFoundException("Active warehouse not found with business unit code: " + warehouse.getBusinessUnitCode());
    }
    /*
      Note:
       1.businessUnitCode will not be nullified/changed of archived warehouse in order to track which warehouse is the new replacement.
       2.stock will also be kept same value so that in future we can know what was the stock at the time of archival.
     */
    entity.isArchived = true;
    entity.archivedAt = java.time.LocalDateTime.now();
    toEntity(warehouse, entity);
  }

  @Override
  public Warehouse findByBusinessUnitCode(String businessUnitCode) {
    return find("businessUnitCode = ?1 and isArchived = false", businessUnitCode)
        .firstResultOptional()
        .map(DbWarehouse::toWarehouse)
        .orElseThrow(() -> new NotFoundException("Active warehouse not found with business unit code: " + businessUnitCode));
  }

  private DbWarehouse toEntity(Warehouse warehouse) {
    DbWarehouse entity = new DbWarehouse();
    toEntity(warehouse, entity);
    return entity;
  }

  private void toEntity(Warehouse source, DbWarehouse target) {
    target.businessUnitCode = source.getBusinessUnitCode();
    target.location = source.getLocation();
    target.capacity = source.getCapacity();
    target.isArchived = source.getIsArchived();
    target.stock = source.getStock();
  }
}
