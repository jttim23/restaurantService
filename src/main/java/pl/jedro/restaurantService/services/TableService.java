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

  public List<Table> findAllTables(Long restaurantId) {
    if (restaurantId == null) {
      throw new IllegalArgumentException();
    }
    return tableRepository.findAll().stream().filter(table -> table.getRestaurant().getId().equals(restaurantId)).collect(Collectors.toList());
  }

  public Table updateTable(Integer peopleQuantity, String description, Long tableId) {
    if (tableId == null) {
      throw new IllegalArgumentException();
    }
    Table savedTable = tableRepository.findById(tableId).orElseThrow(IllegalArgumentException::new);
    if (description != null && !description.isEmpty() && peopleQuantity > 0) {
      savedTable.setPeopleQuantity(peopleQuantity);
      savedTable.setDescription(description);
      tableRepository.save(savedTable);
    }

    return savedTable;
  }


  public AvailableTablesDTO findAvailableTables(String date, Long restaurantId) {
    List<Table> tables = new ArrayList<>();
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
      tables = restaurant.getTables().stream().filter(table -> !table.getState().equals(State.RESERVED)).collect(Collectors.toList());
    }
    AvailableTablesDTO availableTablesDTO = new AvailableTablesDTO();
    availableTablesDTO.setAvailableTables(tables);
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

  public Table setTableState(String state, Long tableId) {
    Table table = tableRepository.findById(tableId).orElseThrow(IllegalArgumentException::new);
    try {
      table.setState(State.valueOf(state));
    } catch (EnumConstantNotPresentException e){
      throw new IllegalArgumentException();
    }
    return table;
  }

}
