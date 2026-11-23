package com.fulfilment.application.monolith.warehouses.domain.usecases;

import com.fulfilment.application.monolith.warehouses.domain.ports.WarehouseStore;
import com.warehouse.api.beans.Warehouse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ArchiveWarehouseUseCaseTest {

  @Test
  void removesWarehouseFromStore() {
    WarehouseStore store = mock(WarehouseStore.class);
    Warehouse warehouse = new Warehouse();
    warehouse.setBusinessUnitCode("MWH-001");
    ArchiveWarehouseUseCase useCase = new ArchiveWarehouseUseCase(store);

    useCase.archive(warehouse);

    verify(store).remove(warehouse);
  }
}
