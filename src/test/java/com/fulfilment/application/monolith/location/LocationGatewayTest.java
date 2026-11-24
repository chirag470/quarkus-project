package com.fulfilment.application.monolith.location;

import com.fulfilment.application.monolith.warehouses.domain.models.Location;
import com.warehouse.api.beans.Warehouse;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LocationGatewayTest {

  @Test
  public void testWhenResolveExistingLocationShouldReturn() {
    // given
     LocationGateway locationGateway = new LocationGateway();

    // when
     Location location = locationGateway.resolveByIdentifier("ZWOLLE-001");

    // then
     assertEquals(location.identification, "ZWOLLE-001");
  }

  @Test
  public void testWarehousesAreKeptByLocationResolver() {
    LocationGateway locationGateway = new LocationGateway();
    Warehouse warehouse = new Warehouse();
    warehouse.setBusinessUnitCode("MWH-001");

    List<Warehouse> warehouseList = locationGateway.getWarehousesLocationMap().getOrDefault("ZWOLLE-001", new ArrayList<>());
    warehouseList.add(warehouse);
    locationGateway.getWarehousesLocationMap().put("ZWOLLE-001", warehouseList);
    assertEquals(1, locationGateway.getWarehousesLocationMap().get("ZWOLLE-001").size());
  }
}
