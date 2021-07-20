package pl.jedro.restaurantService.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.DayOfWeek;
import java.time.LocalTime;
@Data
@Entity
public class OpeningHour {
  @Id
  @GeneratedValue
  private Long id;
  private String dayOfWeek;
  private String fromHour;
  private String toHour;


  public OpeningHour() {
  }

  public OpeningHour(Long id, String dayOfWeek, String fromHour, String toHour) {
    this.id = id;
    this.dayOfWeek = dayOfWeek;
    this.fromHour = fromHour;
    this.toHour = toHour;

  }
}
