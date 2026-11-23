package com.fulfilment.application.monolith.warehouses.domain.ports;

//import com.fulfilment.application.monolith.warehouses.domain.models.Warehouse;
import com.warehouse.api.beans.Warehouse;

public interface ReplaceWarehouseOperation {
  void replace(String businessUnitCode, Warehouse warehouse);
}
