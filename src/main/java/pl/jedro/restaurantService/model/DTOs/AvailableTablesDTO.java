package pl.jedro.restaurantService.model.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.jedro.restaurantService.model.Desk;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvailableTablesDTO {
  private List<Desk> availableDesks;
}
