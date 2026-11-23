package com.fulfilment.application.monolith.warehouses.adapters.database;

//import com.fulfilment.application.monolith.warehouses.domain.models.Warehouse;
import com.warehouse.api.beans.Warehouse;
import jakarta.persistence.Cacheable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "warehouse")
@Cacheable
public class DbWarehouse {

  @Id @GeneratedValue public Long id;

  public String businessUnitCode;

  public String location;

  public Integer capacity;

  public boolean isArchived;

  public Integer stock;

  public LocalDateTime createdAt;

  public LocalDateTime archivedAt;

  public DbWarehouse() {}

  public Warehouse toWarehouse() {
    var warehouse = new Warehouse();
    warehouse.setBusinessUnitCode(this.businessUnitCode);
    warehouse.setLocation(this.location);
    warehouse.setCapacity(this.capacity);
    warehouse.setIsArchived(this.isArchived);
    warehouse.setStock(this.stock);
    return warehouse;
  }
}
