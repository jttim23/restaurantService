package pl.jedro.restaurantService.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Desk {
  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private Long id;
  private Integer peopleQuantity;
  private String description;
  private State state;
  @ManyToOne
  @JsonIgnore
  private Restaurant restaurant;

}
