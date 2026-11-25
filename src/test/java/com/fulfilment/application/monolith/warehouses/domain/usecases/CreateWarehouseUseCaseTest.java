package com.fulfilment.application.monolith.warehouses.domain.usecases;

import com.fulfilment.application.monolith.warehouses.domain.models.Location;
import com.fulfilment.application.monolith.warehouses.domain.ports.LocationResolver;
import com.fulfilment.application.monolith.warehouses.domain.ports.WarehouseStore;
import com.warehouse.api.beans.Warehouse;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

public class CreateWarehouseUseCaseTest {

  @Test
  void createsWarehouseWhenDataIsValid() {
    WarehouseStore store = mock(WarehouseStore.class);
    LocationResolver resolver = mock(LocationResolver.class);
    when(resolver.resolveByIdentifier("AMSTERDAM-001"))
        .thenReturn(new Location("AMSTERDAM-001", 5, 1000));
    CreateWarehouseUseCase useCase = new CreateWarehouseUseCase(store, resolver);
    Warehouse warehouse = warehouse("MWH-NEW", "AMSTERDAM-001", 100, 20);
    when(store.getAll()).thenReturn(List.of());
    when(store.findByLocation("AMSTERDAM-001")).thenReturn(List.of());
    when(store.create(warehouse)).thenReturn(warehouse);

    Warehouse created = useCase.create(warehouse);

    assertSame(warehouse, created);
    verify(store).create(warehouse);
    verify(store).findByLocation("AMSTERDAM-001");
  }

  @Test
  void rejectsDuplicateBusinessUnitCode() {
    WarehouseStore store = mock(WarehouseStore.class);
    when(store.getAll()).thenReturn(List.of(
        warehouse("MWH-EXISTING", "AMSTERDAM-001", 100, 20)));
    LocationResolver resolver = mock(LocationResolver.class);
    when(resolver.resolveByIdentifier("ROTTERDAM-001"))
        .thenReturn(new Location("ROTTERDAM-001", 5, 1000));
    CreateWarehouseUseCase useCase = new CreateWarehouseUseCase(store, resolver);

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> useCase.create(warehouse("MWH-EXISTING", "ROTTERDAM-001", 100, 20)));

    assertEquals("Business Unit code MWH-EXISTING already exists", exception.getMessage());
    verify(store, never()).create(org.mockito.ArgumentMatchers.any(Warehouse.class));
  }

  @Test
  void rejectsMissingLocation() {
    CreateWarehouseUseCase useCase = new CreateWarehouseUseCase(
        mock(WarehouseStore.class),
        mock(LocationResolver.class));
    Warehouse warehouse = warehouse("MWH-NEW", null, 100, 20);

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> useCase.create(warehouse));

    assertEquals("location is required", exception.getMessage());
  }

  @Test
  void rejectsInvalidLocation() {
    LocationResolver resolver = mock(LocationResolver.class);
    when(resolver.resolveByIdentifier("UNKNOWN-001"))
        .thenThrow(new java.util.NoSuchElementException());
    CreateWarehouseUseCase useCase = new CreateWarehouseUseCase(
        mock(WarehouseStore.class),
        resolver);
    Warehouse warehouse = warehouse("MWH-NEW", "UNKNOWN-001", 100, 20);

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> useCase.create(warehouse));

    assertEquals("Location could not be fetched while warehouse creation", exception.getMessage());
  }

  @Test
  void rejectsWarehouseWhenLocationWarehouseLimitIsReached() {
    WarehouseStore store = mock(WarehouseStore.class);
    LocationResolver resolver = mock(LocationResolver.class);
    when(store.getAll()).thenReturn(List.of());
    when(resolver.resolveByIdentifier("AMSTERDAM-001"))
        .thenReturn(new Location("AMSTERDAM-001", 1, 1000));
    when(store.findByLocation("AMSTERDAM-001"))
        .thenReturn(List.of(warehouse("MWH-EXISTING", "AMSTERDAM-001", 100, 20)));
    CreateWarehouseUseCase useCase = new CreateWarehouseUseCase(store, resolver);

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> useCase.create(warehouse("MWH-NEW", "AMSTERDAM-001", 100, 20)));

    assertEquals("Maximum number of warehouses reached for the specified location", exception.getMessage());
    verify(store, never()).create(org.mockito.ArgumentMatchers.any(Warehouse.class));
  }

  @Test
  void rejectsWarehouseWhenLocationCapacityWouldBeExceeded() {
    WarehouseStore store = mock(WarehouseStore.class);
    LocationResolver resolver = mock(LocationResolver.class);
    when(store.getAll()).thenReturn(List.of());
    when(resolver.resolveByIdentifier("AMSTERDAM-001"))
        .thenReturn(new Location("AMSTERDAM-001", 5, 1000));
    when(store.findByLocation("AMSTERDAM-001"))
        .thenReturn(List.of(warehouse("MWH-EXISTING", "AMSTERDAM-001", 950, 20)));
    CreateWarehouseUseCase useCase = new CreateWarehouseUseCase(store, resolver);

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> useCase.create(warehouse("MWH-NEW", "AMSTERDAM-001", 100, 20)));

    assertEquals("Maximum capacity reached for the specified location", exception.getMessage());
    verify(store, never()).create(org.mockito.ArgumentMatchers.any(Warehouse.class));
  }

  private static Warehouse warehouse(String businessUnitCode, String location,
                                      Integer capacity, Integer stock) {
     Warehouse newWarehouse = new Warehouse();
     newWarehouse.setBusinessUnitCode(businessUnitCode);
     newWarehouse.setLocation(location);
     newWarehouse.setCapacity(capacity);
     newWarehouse.setStock(stock);
     return newWarehouse;
  }
}
