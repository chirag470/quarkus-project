package com.fulfilment.application.monolith.location;

import com.fulfilment.application.monolith.warehouses.domain.models.Location;
import com.fulfilment.application.monolith.warehouses.domain.ports.LocationResolver;
import com.warehouse.api.beans.Warehouse;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.*;

import static io.quarkus.hibernate.orm.panache.PanacheEntity_.id;

@ApplicationScoped
public class LocationGateway implements LocationResolver {

  private static final List<Location> locations = new ArrayList<>();

  static {
    locations.add(new Location("ZWOLLE-001", 1, 40));
    locations.add(new Location("ZWOLLE-002", 2, 50));
    locations.add(new Location("AMSTERDAM-001", 5, 100));
    locations.add(new Location("AMSTERDAM-002", 3, 75));
    locations.add(new Location("TILBURG-001", 1, 40));
    locations.add(new Location("HELMOND-001", 1, 45));
    locations.add(new Location("EINDHOVEN-001", 2, 70));
    locations.add(new Location("VETSBY-001", 1, 90));
  }


  @Override
  public Location resolveByIdentifier(String identifier) {
    // TODO implement this method

    if (identifier == null || identifier.isEmpty()) {

      throw new IllegalArgumentException("identifier is required");
    }

    if (locations.isEmpty()) {
      throw new RuntimeException("No locations found");

    }

    return locations.stream()
            .filter(l -> l.identification.equals(identifier))
            .findFirst()
            .orElseThrow(() -> new NoSuchElementException(String.format("Location not found with identifier: %s", identifier)));

    //throw new UnsupportedOperationException("Unimplemented method 'resolveByIdentifier'");
  }
}
