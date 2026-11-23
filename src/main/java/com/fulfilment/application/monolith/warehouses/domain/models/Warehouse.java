package com.fulfilment.application.monolith.warehouses.domain.models;

import java.time.LocalDateTime;

@Deprecated
public class Warehouse {

  // unique identifier
  public String businessUnitCode;

  public String location;

  public Integer capacity;

  public boolean isArchived;

  public Integer stock;

  public LocalDateTime createdAt;

  public LocalDateTime archivedAt;
}
