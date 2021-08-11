package pl.jedro.restaurantService.services;

import org.springframework.stereotype.Service;
import pl.jedro.restaurantService.mappers.TableDTOMapper;
import pl.jedro.restaurantService.model.DTOs.AvailableTablesDTO;
import pl.jedro.restaurantService.model.DTOs.TableDTO;
import pl.jedro.restaurantService.model.Desk;
import pl.jedro.restaurantService.model.OpeningHour;
import pl.jedro.restaurantService.model.Restaurant;
import pl.jedro.restaurantService.model.State;
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
DeskService {
  private TableRepository tableRepository;
  private RestaurantRepository restaurantRepository;
  private TableDTOMapper tableDTOMapper;

  public DeskService(TableRepository tableRepository, RestaurantRepository restaurantRepository) {
    this.tableRepository = tableRepository;
    this.restaurantRepository = restaurantRepository;
    tableDTOMapper = TableDTOMapper.INSTANCE;
  }

  public List<TableDTO> findAllTables(Long restaurantId) {
    if (restaurantId == null) {
      throw new IllegalArgumentException();
    }
    return tableRepository.findAll().stream().filter(table -> table.getRestaurant().getId().equals(restaurantId))
        .map(desk -> tableDTOMapper.deskToTableDTO(desk)).collect(Collectors.toList());
  }

  public TableDTO updateTable(Integer peopleQuantity, String description, Long tableId) {
    if (tableId == null) {
      throw new IllegalArgumentException();
    }
    Desk savedDesk = tableRepository.findById(tableId).orElseThrow(IllegalArgumentException::new);
    if (description != null && !description.isEmpty() && peopleQuantity > 0) {
      savedDesk.setPeopleQuantity(peopleQuantity);
      savedDesk.setDescription(description);
      tableRepository.save(savedDesk);
    }

    return tableDTOMapper.deskToTableDTO(savedDesk);
  }


  public AvailableTablesDTO findAvailableTables(String date, Long restaurantId) {
    List<Desk> desks = new ArrayList<>();
    if (date == null || date.isEmpty() || restaurantId == null) {
      throw new IllegalArgumentException();
    }
    LocalDateTime dateTime;
    try {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
      dateTime = LocalDateTime.parse(date, formatter);
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
    if (openedDayOptional.isEmpty()) {
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

  public TableDTO setTableState(String status, Long tableId) {
    Desk desk = tableRepository.findById(tableId).orElseThrow(IllegalArgumentException::new);
    State state;
    try {
      state = State.valueOf(status);
    } catch (EnumConstantNotPresentException e) {
      throw new IllegalArgumentException();
    }
    if (desk.getState().equals(state)) {
      return tableDTOMapper.deskToTableDTO(desk);
    } else desk.setState(state);
    return tableDTOMapper.deskToTableDTO(desk);
  }

}
