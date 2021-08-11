package pl.jedro.restaurantService.model.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantDTO {
  private Long id;
  private String emailAddress;
  private String password;
  private String phoneNumber;


}
