package pl.jedro.restaurantService.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Address {
  @Id
  @GeneratedValue
  private Long id;
  private String street;
  private String apartmentNumber;
  private String zipCode;
  private String city;

  public Address() {
  }

  public Address(Long id, String street, String apartmentNumber, String zipCode, String city) {
    this.id = id;
    this.street = street;
    this.apartmentNumber = apartmentNumber;
    this.zipCode = zipCode;
    this.city = city;
  }
}

