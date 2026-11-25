package com.fulfilment.application.monolith.warehouses.domain.usecases;

//import com.fulfilment.application.monolith.warehouses.domain.models.Warehouse;
import com.warehouse.api.beans.Warehouse;
import com.fulfilment.application.monolith.warehouses.domain.ports.ArchiveWarehouseOperation;
import com.fulfilment.application.monolith.warehouses.domain.ports.ReplaceWarehouseOperation;
import com.fulfilment.application.monolith.warehouses.domain.ports.WarehouseStore;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@ApplicationScoped
public class ReplaceWarehouseUseCase implements ReplaceWarehouseOperation {

  private final WarehouseStore warehouseStore;
  private final ArchiveWarehouseOperation archiveWarehouseOperation;
  private final CreateWarehouseUseCase createWarehouseUseCase;

  @Inject
  public ReplaceWarehouseUseCase(WarehouseStore warehouseStore, ArchiveWarehouseOperation archiveWarehouseOperation, CreateWarehouseUseCase createWarehouseUseCase) {
    this.warehouseStore = warehouseStore;
    this.archiveWarehouseOperation = archiveWarehouseOperation;
      this.createWarehouseUseCase = createWarehouseUseCase;
    }

  @Override
  @Transactional
  public void replace(String businessUnitCode, Warehouse newWarehouse) {
    // TODO implement this method

    Warehouse currentWarehouse = warehouseStore.findByBusinessUnitCode(businessUnitCode);

    if (newWarehouse.getBusinessUnitCode() != null) {
      throw new IllegalArgumentException("Business Unit code already present in input");
    }

    if (!newWarehouse.getLocation().equals(currentWarehouse.getLocation())) {
      throw new IllegalArgumentException("Location should be same for replacement");
    }

    if (newWarehouse.getCapacity() < currentWarehouse.getCapacity()) {
      throw new IllegalArgumentException("New capacity can not accommodate the stock of warehouse to be archived");
    }

    // archive
    archiveWarehouseOperation.archive(currentWarehouse);

    newWarehouse.setBusinessUnitCode(businessUnitCode);
    Warehouse createdWarehouse = createWarehouseUseCase.create(newWarehouse);
    createdWarehouse.setStock(currentWarehouse.getStock()); // Stock transferred and matched
    warehouseStore.update(createdWarehouse);
  }
}
