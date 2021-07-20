package pl.jedro.restaurantService.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Data
public class Table {
  @Id
  @GeneratedValue
  private Long id;
  private Integer peopleQuantity;
  private String description;
  @ManyToOne
  @JsonIgnore
  private Restaurant restaurant;

  public Table() {
  }

  public Table(Long id, Integer peopleQuantity, String description, Restaurant restaurant) {
    this.id = id;
    this.peopleQuantity = peopleQuantity;
    this.description = description;
    this.restaurant = restaurant;
  }
}
