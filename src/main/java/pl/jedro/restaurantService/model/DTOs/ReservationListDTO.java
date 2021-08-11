package pl.jedro.restaurantService.model.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.jedro.restaurantService.model.Reservation;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationListDTO {
  List<Reservation> reservations;
}
