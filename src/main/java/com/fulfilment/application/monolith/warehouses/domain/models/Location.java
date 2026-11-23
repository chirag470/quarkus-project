package com.fulfilment.application.monolith.warehouses.domain.models;


import com.warehouse.api.beans.Warehouse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Location {
  public String identification;

  // maximum number of warehouses that can be created in this location
  public int maxNumberOfWarehouses;

  // maximum capacity of the location summing all the warehouse capacities
  public int maxCapacity;

  public Map<String, List<Warehouse>> locationWarehouseMap;

  public Location(String identification, int maxNumberOfWarehouses, int maxCapacity) {
    this.identification = identification;
    this.maxNumberOfWarehouses = maxNumberOfWarehouses;
    this.maxCapacity = maxCapacity;
    locationWarehouseMap = new HashMap<>();
  }
}
