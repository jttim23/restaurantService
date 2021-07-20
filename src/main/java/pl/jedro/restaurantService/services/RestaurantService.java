package pl.jedro.restaurantService.services;

import org.springframework.stereotype.Service;
import pl.jedro.restaurantService.model.Address;
import pl.jedro.restaurantService.model.OpeningHour;
import pl.jedro.restaurantService.model.Restaurant;
import pl.jedro.restaurantService.model.Table;
import pl.jedro.restaurantService.repositories.RestaurantRepository;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RestaurantService {

  private RestaurantRepository restaurantRepository;


  public RestaurantService(RestaurantRepository restaurantRepository) {
    this.restaurantRepository = restaurantRepository;
  }

  public Restaurant findRestaurantById(Long restaurantId) {
    if (restaurantId == null) {
      throw new IllegalArgumentException();
    }
    return restaurantRepository.findById(restaurantId).orElseThrow(IllegalArgumentException::new);
  }

  public Restaurant saveNewRestaurant(Restaurant restaurant) {
    if (restaurant == null) {
      throw new IllegalArgumentException();
    }
    restaurantRepository.save(restaurant);
    return restaurant;
  }

  public Restaurant saveNewTables(List<Table> newTables, Long restaurantId) {
    if (restaurantId == null || newTables == null || newTables.size() < 1) {
      throw new IllegalArgumentException();
    }
    Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(IllegalArgumentException::new);
    if (!(restaurant.getTables().size() > 0)) {
      newTables = newTables.stream().filter(Objects::nonNull).collect(Collectors.toList());
      restaurant.setTables(newTables);
      restaurantRepository.save(restaurant);
    }
    return restaurant;
  }

  public Restaurant saveNewAddress(Address address, Long restaurantId) {
    if (address == null || restaurantId == null) {
      throw new IllegalArgumentException();
    }
    Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(IllegalArgumentException::new);
    if (restaurant.getAddress() == null) {
      restaurant.setAddress(address);
      restaurantRepository.save(restaurant);
    }
    return restaurant;
  }

  public Address updateAddress(String street, String apartmentNumber, String zipCode, String city, Long restaurantId) {
    if (restaurantId == null || street == null || apartmentNumber == null || zipCode == null || city == null) {
      throw new IllegalArgumentException();
    }
    Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(IllegalArgumentException::new);
    Address address = restaurant.getAddress();
    address.setStreet(street);
    address.setApartmentNumber(apartmentNumber);
    address.setZipCode(zipCode);
    address.setCity(city);
    restaurant.setAddress(address);
    restaurantRepository.save(restaurant);
    return address;
  }

  public Address findAddress(Long restaurantId) {
    if (restaurantId == null) {
      throw new IllegalArgumentException();
    }
    Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(IllegalArgumentException::new);
    return restaurant.getAddress();
  }

  public Set<OpeningHour> findOpeningHours(Long restaurantId) {
    if (restaurantId == null) {
      throw new IllegalArgumentException();
    }
    Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(IllegalArgumentException::new);
    return restaurant.getOpeningHours();
  }

  public Restaurant saveNewOpeningHours(Set<OpeningHour> openingHours, Long restaurantId) {
    if (openingHours == null || restaurantId == null) {
      throw new IllegalArgumentException();
    }
    Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(IllegalArgumentException::new);
    if (restaurant.getOpeningHours() == null) {
      restaurant.setOpeningHours(openingHours);
      restaurantRepository.save(restaurant);
    }
    return restaurant;
  }


  public Restaurant saveNewTable(Table newTable, Long restaurantId) {
    if (restaurantId == null || newTable == null) {
      throw new IllegalArgumentException();
    }
    Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(IllegalArgumentException::new);
    List<Table> tables = restaurant.getTables();
    tables.add(newTable);
    restaurant.setTables(tables);
    restaurantRepository.save(restaurant);
    return restaurant;
  }
}
