package pl.jedro.restaurantService.model.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.jedro.restaurantService.model.State;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableDTO {
  private Long id;
  private Integer peopleQuantity;
  private String description;
  private State state;
}
