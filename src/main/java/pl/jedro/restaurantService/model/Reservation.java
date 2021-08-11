package pl.jedro.restaurantService.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Reservation {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String customerName;
  private String customerEmail;
  private String customerPhoneNumber;
  private String additionalInfo;
  private Long restaurantId;
  private Long deskId;
  private State state;

  public Reservation() {
  }


}
