package com.fulfilment.application.monolith.warehouses.domain.ports;

//import com.fulfilment.application.monolith.warehouses.domain.models.Warehouse;
import com.warehouse.api.beans.Warehouse;
public interface CreateWarehouseOperation {
  Warehouse create(Warehouse warehouse);
}
