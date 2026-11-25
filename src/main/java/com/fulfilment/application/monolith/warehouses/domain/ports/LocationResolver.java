package com.fulfilment.application.monolith.warehouses.domain.ports;

import com.fulfilment.application.monolith.warehouses.domain.models.Location;
import com.warehouse.api.beans.Warehouse;

import java.util.List;
import java.util.Map;

public interface LocationResolver {
  Location resolveByIdentifier(String identifier);
}
