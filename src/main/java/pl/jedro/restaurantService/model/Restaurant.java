package pl.jedro.restaurantService.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant {

  @Id
  @GeneratedValue
  private Long id;
  private String emailAddress;
  private String password;
  private String phoneNumber;
  @OneToOne(cascade= CascadeType.ALL)
  private Address address;
  @OneToMany(cascade= CascadeType.ALL)
  private Set<OpeningHour> openingHours;
  @OneToMany(cascade= CascadeType.ALL)
  private List<Table> tables;


}
