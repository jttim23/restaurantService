package pl.jedro.restaurantService.services;

import org.springframework.stereotype.Service;
import pl.jedro.restaurantService.model.*;
import pl.jedro.restaurantService.repositories.RestaurantRepository;
import pl.jedro.restaurantService.repositories.TableRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class
TableService {
  private TableRepository tableRepository;
  private RestaurantRepository restaurantRepository;

  public TableService(TableRepository tableRepository, RestaurantRepository restaurantRepository) {
    this.tableRepository = tableRepository;
    this.restaurantRepository = restaurantRepository;
  }

  public List<Desk> findAllTables(Long restaurantId) {
    if (restaurantId == null) {
      throw new IllegalArgumentException();
    }
    return tableRepository.findAll().stream().filter(table -> table.getRestaurant().getId().equals(restaurantId)).collect(Collectors.toList());
  }

  public Desk updateTable(Integer peopleQuantity, String description, Long tableId) {
    if (tableId == null) {
      throw new IllegalArgumentException();
    }
    Desk savedDesk = tableRepository.findById(tableId).orElseThrow(IllegalArgumentException::new);
    if (description != null && !description.isEmpty() && peopleQuantity > 0) {
      savedDesk.setPeopleQuantity(peopleQuantity);
      savedDesk.setDescription(description);
      tableRepository.save(savedDesk);
    }

    return savedDesk;
  }


  public AvailableTablesDTO findAvailableTables(String date, Long restaurantId) {
    List<Desk> desks = new ArrayList<>();
    if (date == null || date.isEmpty() || restaurantId == null) {
      throw new IllegalArgumentException();
    }
    LocalDateTime dateTime;
    try {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
      dateTime = LocalDateTime.parse(date,formatter);
    } catch (DateTimeParseException e) {
      throw new IllegalArgumentException(e.getMessage());
    }
    Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(IllegalArgumentException::new);
    if (restaurantIsOpen(dateTime, restaurant)) {
      desks = restaurant.getDesks().stream().filter(table -> !table.getState().equals(State.RESERVED)).collect(Collectors.toList());
    }
    AvailableTablesDTO availableTablesDTO = new AvailableTablesDTO();
    availableTablesDTO.setAvailableDesks(desks);
    return availableTablesDTO;
  }

  public boolean restaurantIsOpen(LocalDateTime dateTime, Restaurant restaurant) {
    Set<OpeningHour> openingHours = restaurant.getOpeningHours();
    Optional<OpeningHour> openedDayOptional = openingHours.stream().filter(openingHour -> openingHour.getDayOfWeek().toString().equalsIgnoreCase(dateTime.getDayOfWeek().name()))
        .findAny();
    if (openedDayOptional.isEmpty()){
      return false;
    }
    OpeningHour openedDay = openedDayOptional.get();

      try {
        if (openedDay.getFromHour().isAfter(dateTime.toLocalTime()) || openedDay.getToHour().isBefore(dateTime.toLocalTime())) {
          return false;
        }
        return true;
      } catch (DateTimeParseException e) {
        throw new IllegalArgumentException(e.getMessage());
      }
    }

  public Desk setTableState(String state, Long tableId) {
    Desk desk = tableRepository.findById(tableId).orElseThrow(IllegalArgumentException::new);
    try {
      desk.setState(State.valueOf(state));
    } catch (EnumConstantNotPresentException e){
      throw new IllegalArgumentException();
    }
    return desk;
  }

}
