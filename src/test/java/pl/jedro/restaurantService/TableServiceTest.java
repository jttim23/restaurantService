package pl.jedro.restaurantService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.jedro.restaurantService.model.*;
import pl.jedro.restaurantService.repositories.RestaurantRepository;
import pl.jedro.restaurantService.repositories.TableRepository;
import pl.jedro.restaurantService.services.TableService;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class TableServiceTest {
  @Mock
  private TableRepository repository;
  @Mock
  private RestaurantRepository restaurantRepository;
  private TableService service;
  private Table newTable;
  private List<Table> tables;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    service = new TableService(repository, restaurantRepository);
    newTable = new Table(1L, 4, "", State.RESERVED, new Restaurant());
    tables = new ArrayList<>();
    tables.add(newTable);
  }

  @Test
  public void returnsOldTableWhenNewDescriptionIsEmpty() {
    when(repository.findById(anyLong())).thenReturn(Optional.of(newTable));
    Table updatedTable = service.updateTable(4, "", 1l);
    assertEquals(newTable.getDescription(), updatedTable.getDescription());
  }

  @Test
  public void returnsOldTableWhenNewPeopleLessThan1() {
    newTable.setDescription("test");
    when(repository.findById(anyLong())).thenReturn(Optional.of(newTable));
    Table updatedTable = service.updateTable(0, "newDesc", 1l);
    assertEquals(newTable.getDescription(), updatedTable.getDescription());
  }

  @Test
  public void returnsOldTableWhenNewDescriptionIsNull() {
    when(repository.findById(anyLong())).thenReturn(Optional.of(newTable));
    Table updatedTable = service.updateTable(4, null, 1l);
    assertEquals(newTable.getDescription(), updatedTable.getDescription());
  }

  @Test
  public void returnsAvailableTableIfDateIsGood() {
    Set<OpeningHour> openingHours = new HashSet<>();
    OpeningHour openingHour = new OpeningHour();
    openingHour.setDayOfWeek(DayOfWeek.MONDAY);
    openingHour.setFromHour(LocalTime.of(8, 0));
    openingHour.setToHour(LocalTime.of(10, 0));
    openingHours.add(openingHour);
    when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(new Restaurant(1l, "", "", "", new Address(), openingHours, tables)));
    assertTrue(service.findAvailableTables("2021-07-19 08:30", 1L).getAvailableTables().size() > 0);
  }

  @Test
  public void notReturnsAvailableTableIfIsReserved() {
    newTable.setState(State.RESERVED);
    Set<OpeningHour> openingHours = new HashSet<>();
    OpeningHour openingHour = new OpeningHour();
    openingHour.setDayOfWeek(DayOfWeek.MONDAY);
    openingHour.setFromHour(LocalTime.of(8, 0));
    openingHour.setToHour(LocalTime.of(10, 0));
    openingHours.add(openingHour);
    when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(new Restaurant(1L, "", "", "", new Address(), openingHours, tables)));
    assertFalse(service.findAvailableTables("2021-07-19 08:30", 1L).getAvailableTables().size() > 0);
  }

  @Test
  public void notReturnsAvailableTableIfDayIsWrong() {
    Set<OpeningHour> openingHours = new HashSet<>();
    OpeningHour openingHour = new OpeningHour();
    openingHour.setDayOfWeek(DayOfWeek.FRIDAY);
    openingHour.setFromHour(LocalTime.of(8, 0));
    openingHour.setToHour(LocalTime.of(10, 0));
    openingHours.add(openingHour);
    when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(new Restaurant(1L, "", "", "", new Address(), openingHours, tables)));
    assertFalse(service.findAvailableTables("2021-07-19 08:30", 1L).getAvailableTables().size() > 0);
  }

  @Test
  public void returnsTrueIfDateIsGood() {
    Set<OpeningHour> openingHours = new HashSet<>();
    OpeningHour openingHour = new OpeningHour();
    openingHour.setDayOfWeek(DayOfWeek.MONDAY);
    openingHour.setFromHour(LocalTime.of(8, 0));
    openingHour.setToHour(LocalTime.of(10, 0));
    openingHours.add(openingHour);
    Restaurant restaurant = new Restaurant(1l, "", "", "", new Address(), openingHours, tables);
    assertTrue(service.restaurantIsOpen(LocalDateTime.of(2021, 07, 19, 7, 30), restaurant));
  }

  @Test
  public void returnsFalseIfDayIsWrong() {
    Set<OpeningHour> openingHours = new HashSet<>();
    OpeningHour openingHour = new OpeningHour();
    openingHour.setDayOfWeek(DayOfWeek.FRIDAY);
    openingHour.setFromHour(LocalTime.of(8, 0));
    openingHour.setToHour(LocalTime.of(10, 0));
    openingHours.add(openingHour);
    Restaurant restaurant = new Restaurant(1l, "", "", "", new Address(), openingHours, tables);
    assertFalse(service.restaurantIsOpen(LocalDateTime.of(2021, 07, 19, 7, 30), restaurant));
  }

  @Test
  public void returnsFalseIfHourIsBeforeOpening() {
    Set<OpeningHour> openingHours = new HashSet<>();
    OpeningHour openingHour = new OpeningHour();
    openingHour.setDayOfWeek(DayOfWeek.MONDAY);
    openingHour.setFromHour(LocalTime.of(8, 0));
    openingHour.setToHour(LocalTime.of(10, 0));
    openingHours.add(openingHour);
    Restaurant restaurant = new Restaurant(1l, "", "", "", new Address(), openingHours, tables);
    assertFalse(service.restaurantIsOpen(LocalDateTime.of(2021, 07, 19, 7, 30), restaurant));
  }
}