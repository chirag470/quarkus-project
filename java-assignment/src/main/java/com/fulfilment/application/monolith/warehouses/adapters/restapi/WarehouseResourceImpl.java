package com.fulfilment.application.monolith.warehouses.adapters.restapi;

import com.fulfilment.application.monolith.warehouses.adapters.database.WarehouseRepository;
import com.fulfilment.application.monolith.warehouses.domain.ports.ArchiveWarehouseOperation;
import com.fulfilment.application.monolith.warehouses.domain.ports.CreateWarehouseOperation;
import com.fulfilment.application.monolith.warehouses.domain.ports.ReplaceWarehouseOperation;
import com.fulfilment.application.monolith.warehouses.domain.ports.WarehouseStore;
import com.warehouse.api.WarehouseResource;
//import com.fulfilment.application.monolith.warehouses.domain.models.Warehouse;
import com.warehouse.api.beans.Warehouse;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.NoSuchElementException;

@RequestScoped
public class WarehouseResourceImpl implements WarehouseResource {

  @Inject private WarehouseStore warehouseStore;

  @Inject private CreateWarehouseOperation createWarehouseOperation;

  @Inject private ReplaceWarehouseOperation replaceWarehouseOperation;

  @Inject private ArchiveWarehouseOperation archiveWarehouseOperation;

  @Override
  public List<Warehouse> listAllWarehousesUnits() {
    return warehouseStore.getAll().stream().map(this::toWarehouseResponse).toList();
  }

  @Override
  public Warehouse createANewWarehouseUnit(@NotNull Warehouse data) {
    // TODO Auto-generated method stub
    return createWarehouseOperation.create(data);
    //throw new UnsupportedOperationException("Unimplemented method 'createANewWarehouseUnit'");
  }

  @Override
  public Warehouse getAWarehouseUnitByID(String identifier) {
    // TODO Auto-generated method stub

    Warehouse selectedWarehouse = warehouseStore.getAll().stream()
            .filter(warehouse -> warehouse.getBusinessUnitCode().equals(identifier))
            .findFirst()
            .orElseThrow(() -> new NoSuchElementException(String.format("Warehouse not found with identifier: %s", identifier)));

    return this.toWarehouseResponse(selectedWarehouse);
    //throw new UnsupportedOperationException("Unimplemented method 'getAWarehouseUnitByID'");
  }

  @Override
  public void archiveAWarehouseUnitByID(String id) {
    // TODO Auto-generated method stub
    archiveWarehouseOperation.archive(warehouseStore.findByBusinessUnitCode(id));
    //throw new UnsupportedOperationException("Unimplemented method 'archiveAWarehouseUnitByID'");
  }

  @Override
  public Warehouse replaceTheCurrentActiveWarehouse(
      String businessUnitCode, @NotNull Warehouse data) {

    replaceWarehouseOperation.replace(businessUnitCode, data);
    return data;
    // TODO Auto-generated method stub

    //throw new UnsupportedOperationException(
      //  "Unimplemented method 'replaceTheCurrentActiveWarehouse'");
  }

  private Warehouse toWarehouseResponse(
      Warehouse warehouse) {
    var response = new Warehouse();
    response.setBusinessUnitCode(warehouse.getBusinessUnitCode());
    response.setLocation(warehouse.getLocation());
    response.setCapacity(warehouse.getCapacity());
    response.setStock(warehouse.getStock());
    response.setIsArchived(warehouse.getIsArchived());

    return response;
  }
}
