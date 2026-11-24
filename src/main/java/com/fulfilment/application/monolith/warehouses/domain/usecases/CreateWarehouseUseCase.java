package com.fulfilment.application.monolith.warehouses.domain.usecases;

import com.fulfilment.application.monolith.warehouses.domain.models.Location;
//import com.fulfilment.application.monolith.warehouses.domain.models.Warehouse;
import com.warehouse.api.beans.Warehouse;
import com.fulfilment.application.monolith.warehouses.domain.ports.CreateWarehouseOperation;
import com.fulfilment.application.monolith.warehouses.domain.ports.LocationResolver;
import com.fulfilment.application.monolith.warehouses.domain.ports.WarehouseStore;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
public class CreateWarehouseUseCase implements CreateWarehouseOperation {

  private final WarehouseStore warehouseStore;
  private final LocationResolver locationResolver;

  @Inject
  public CreateWarehouseUseCase(WarehouseStore warehouseStore,
                                LocationResolver locationResolver) {
    this.warehouseStore = warehouseStore;
    this.locationResolver = locationResolver;
  }

  @Override
  public Warehouse create(Warehouse data) {
    // TODO implement this method

    boolean businessCodeExists = warehouseStore.getAll()
            .stream()
            .anyMatch(warehouse -> warehouse.getBusinessUnitCode().equals(data.getBusinessUnitCode()));

    if (businessCodeExists) {
      throw new IllegalArgumentException(String.format("Business Unit code %s already exists", data.getBusinessUnitCode()));
    }

    if (data.getLocation() == null || data.getLocation().isEmpty()) {
      throw new IllegalArgumentException("location is required");
    }

    Location fetchedLocation = null;
    try {
      fetchedLocation = locationResolver.resolveByIdentifier(data.getLocation());
    } catch (Exception e) {
      throw new IllegalArgumentException("Location could not be fetched while warehouse creation");
    }

    List<Warehouse> locationWarehouses = locationResolver.getWarehousesLocationMap()
                .get(data.getLocation())
                .stream()
                .filter(warehouse -> Boolean.TRUE.equals(warehouse.getIsArchived()))
                .toList();

    if (locationWarehouses.size() >= fetchedLocation.maxNumberOfWarehouses) {
      throw new IllegalArgumentException("Maximum number of warehouses reached for the specified location");
    }

    int currentSizeOfLocation =
        locationWarehouses
                .stream()
                .filter(warehouse -> Boolean.TRUE.equals(warehouse.getIsArchived()))
                .mapToInt(Warehouse::getCapacity)
                .sum();
    if (currentSizeOfLocation + data.getCapacity() > fetchedLocation.maxCapacity) {
      throw new IllegalArgumentException("Maximum capacity reached for the specified location");
    }

    // if all went well, create the warehouse
    Warehouse warehouse = warehouseStore.create(data);
    List<Warehouse> warehouses = locationResolver.getWarehousesLocationMap().get(fetchedLocation.identification);
    warehouses.add(warehouse);
    return warehouse;
  }
}
