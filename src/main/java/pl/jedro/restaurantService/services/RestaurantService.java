package pl.jedro.restaurantService.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pl.jedro.restaurantService.mappers.RestaurantDTOMapper;
import pl.jedro.restaurantService.mappers.TableDTOMapper;
import pl.jedro.restaurantService.model.*;
import pl.jedro.restaurantService.model.DTOs.ReservationListDTO;
import pl.jedro.restaurantService.model.DTOs.RestaurantDTO;
import pl.jedro.restaurantService.model.DTOs.TableDTO;
import pl.jedro.restaurantService.repositories.RestaurantRepository;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RestaurantService {

  private RestaurantRepository restaurantRepository;
  private RestTemplate restTemplate;
  private RestaurantDTOMapper restaurantDTOMapper = RestaurantDTOMapper.INSTANCE;

  public RestaurantService(RestaurantRepository restaurantRepository, RestTemplate restTemplate) {
    this.restaurantRepository = restaurantRepository;
    this.restTemplate = restTemplate;
  }

  public Restaurant findRestaurantById(Long restaurantId) {
    if (restaurantId == null) {
      throw new IllegalArgumentException();
    }
    return restaurantRepository.findById(restaurantId).orElseThrow(IllegalArgumentException::new);
  }

  public RestaurantDTO saveNewRestaurant(RestaurantDTO restaurantDTO) {
    if (restaurantDTO == null) {
      throw new IllegalArgumentException();
    }
    Restaurant restaurant = restaurantDTOMapper.restaurantDTOtoRestaurant(restaurantDTO);
    restaurantRepository.save(restaurant);
    return restaurantDTOMapper.restaurantToRestaurantDTO(restaurant);
  }

  public Restaurant saveNewTables(List<TableDTO> newTables, Long restaurantId) {
    if (restaurantId == null || newTables == null || newTables.size() < 1) {
      throw new IllegalArgumentException();
    }
    Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(IllegalArgumentException::new);
    if (!(restaurant.getDesks().size() > 0)) {
      List<Desk> newDesks = newTables.stream().map(TableDTOMapper.INSTANCE::tableDTOtoDesk).collect(Collectors.toList());
      newDesks = newDesks.stream().filter(Objects::nonNull).collect(Collectors.toList());
      newDesks.forEach(table -> {
        table.setRestaurant(restaurant);
        table.setState(State.FREE);
      });
      restaurant.setDesks(newDesks);
      restaurantRepository.save(restaurant);
    }
    return restaurant;
  }

  public Restaurant saveNewTable(TableDTO newTable, Long restaurantId) {
    if (restaurantId == null || newTable == null) {
      throw new IllegalArgumentException();
    }
    Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(IllegalArgumentException::new);
    Desk newDesk = TableDTOMapper.INSTANCE.tableDTOtoDesk(newTable);
    newDesk.setRestaurant(restaurant);
    newDesk.setState(State.FREE);
    List<Desk> desks = restaurant.getDesks();
    desks.add(newDesk);
    restaurant.setDesks(desks);
    restaurantRepository.save(restaurant);
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
    if (!(restaurant.getOpeningHours().size() > 0)) {
      restaurant.setOpeningHours(openingHours);
      restaurantRepository.save(restaurant);
    }
    return restaurant;
  }


  public ReservationListDTO getAllReservations(Long restaurantId) {
    String url = "https://RESERVATIONSERVICE/v1/reservations/all/" + restaurantId;
    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
    return restTemplate.getForObject(builder.toUriString(), ReservationListDTO.class);
  }
}
