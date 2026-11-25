package com.fulfilment.application.monolith.warehouses.domain.ports;

//import com.fulfilment.application.monolith.warehouses.domain.models.Warehouse;
import com.warehouse.api.beans.Warehouse;
import java.util.List;

public interface WarehouseStore {

  List<Warehouse> getAll();

  Warehouse create(Warehouse warehouse);

  void update(Warehouse warehouse);

  void remove(Warehouse warehouse);

  Warehouse findByBusinessUnitCode(String buCode);

  List<Warehouse> findByLocation(String locationIdentifier);

}
