package com.fulfilment.application.monolith.warehouses.domain.usecases;

import com.fulfilment.application.monolith.warehouses.domain.ports.ArchiveWarehouseOperation;
import com.fulfilment.application.monolith.warehouses.domain.ports.WarehouseStore;
import com.warehouse.api.beans.Warehouse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ReplaceWarehouseUseCaseTest {

  @Test
  void replacesWarehouseAndTransfersStock() {
    WarehouseStore store = mock(WarehouseStore.class);
    ArchiveWarehouseOperation archive = mock(ArchiveWarehouseOperation.class);
    CreateWarehouseUseCase create = mock(CreateWarehouseUseCase.class);
    Warehouse current = warehouse("MWH-001", "AMSTERDAM-001", 100, 40);
    when(store.findByBusinessUnitCode("MWH-001")).thenReturn(current);
    Warehouse replacement = warehouse(null, "AMSTERDAM-001", 150, 999);
    when(create.create(replacement)).thenReturn(replacement);
    doAnswer(invocation -> {
      Warehouse archivedWarehouse = invocation.getArgument(0);
      archivedWarehouse.setIsArchived(true);
      return null;
    }).when(archive).archive(any(Warehouse.class));
    ReplaceWarehouseUseCase useCase = new ReplaceWarehouseUseCase(store, archive, create);

    useCase.replace("MWH-001", replacement);

    verify(archive).archive(current);
    verify(create).create(replacement);
    verify(store).update(replacement);
    assertTrue(current.getIsArchived());
    assertEquals("MWH-001", replacement.getBusinessUnitCode());
    assertEquals(current.getBusinessUnitCode(), replacement.getBusinessUnitCode());
    assertEquals(40, replacement.getStock());
  }

  @Test
  void rejectsReplacementAtAnotherLocation() {
    WarehouseStore store = mock(WarehouseStore.class);
    ArchiveWarehouseOperation archive = mock(ArchiveWarehouseOperation.class);
    CreateWarehouseUseCase create = mock(CreateWarehouseUseCase.class);

    Warehouse current = warehouse("MWH-001", "AMSTERDAM-001", 100, 40);
    when(store.findByBusinessUnitCode("MWH-001")).thenReturn(current);
    ReplaceWarehouseUseCase useCase =
        new ReplaceWarehouseUseCase(store, archive, create);

    Warehouse replacement = warehouse(null, "ROTTERDAM-001", 150, 40);

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> useCase.replace("MWH-001", replacement));

    assertEquals("Location should be same for replacement", exception.getMessage());
    verify(archive, never()).archive(any(Warehouse.class));
    verify(create, never()).create(any(Warehouse.class));
  }

  @Test
  void rejectsCapacityBelowCurrentStock() {
    WarehouseStore store = mock(WarehouseStore.class);
    ArchiveWarehouseOperation archive = mock(ArchiveWarehouseOperation.class);
    Warehouse current = warehouse("MWH-001", "AMSTERDAM-001", 100, 40);
    when(store.findByBusinessUnitCode("MWH-001")).thenReturn(current);
    CreateWarehouseUseCase create = mock(CreateWarehouseUseCase.class);
    ReplaceWarehouseUseCase useCase =
        new ReplaceWarehouseUseCase(store, archive, create);

    Warehouse replacement = warehouse(null, "AMSTERDAM-001", 30, 40);

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> useCase.replace("MWH-001", replacement));

    assertEquals("New capacity can not accommodate the stock of warehouse to be archived",
        exception.getMessage());
    verify(archive, never()).archive(any(Warehouse.class));
    verify(create, never()).create(any(Warehouse.class));
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
