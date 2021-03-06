package pl.jedro.restaurantService.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class OpeningHour {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private DayOfWeek dayOfWeek;
  private LocalTime fromHour;
  private LocalTime toHour;

}
