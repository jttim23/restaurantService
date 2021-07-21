package pl.jedro.restaurantService.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Table {
  @Id
  @GeneratedValue
  private Long id;
  private Integer peopleQuantity;
  private String description;
  private State state;
  @ManyToOne
  @JsonIgnore
  private Restaurant restaurant;

}
