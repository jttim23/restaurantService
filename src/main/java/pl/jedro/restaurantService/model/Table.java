package pl.jedro.restaurantService.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Table {
  @Id
  @GeneratedValue
  private Long id;
  private Integer peopleQuantity;
  private String description;
  private Long restaurantId;

  public Table() {
  }

  public Table(Long id, Integer peopleQuantity, String description, Long restaurantId) {
    this.id = id;
    this.peopleQuantity = peopleQuantity;
    this.description = description;
    this.restaurantId = restaurantId;
  }
}
