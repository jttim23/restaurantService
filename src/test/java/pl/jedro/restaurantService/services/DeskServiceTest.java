package pl.jedro.restaurantService.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.jedro.restaurantService.model.*;
import pl.jedro.restaurantService.model.DTOs.TableDTO;
import pl.jedro.restaurantService.repositories.RestaurantRepository;
import pl.jedro.restaurantService.repositories.TableRepository;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class DeskServiceTest {
  @Mock
  private TableRepository repository;
  @Mock
  private RestaurantRepository restaurantRepository;
  private DeskService service;
  private Desk newDesk;
  private List<Desk> desks;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    service = new DeskService(repository, restaurantRepository);
    newDesk = new Desk(1L, 4, "", State.FREE, new Restaurant());
    desks = new ArrayList<>();
    desks.add(newDesk);
  }

  @Test
  public void returnsOldTableWhenNewDescriptionIsEmpty() {
    when(repository.findById(anyLong())).thenReturn(Optional.of(newDesk));
    TableDTO updatedDesk = service.updateTable(4, "", 1l);
    assertEquals(newDesk.getDescription(), updatedDesk.getDescription());
  }

  @Test
  public void returnsOldTableWhenNewPeopleLessThan1() {
    newDesk.setDescription("test");
    when(repository.findById(anyLong())).thenReturn(Optional.of(newDesk));
    TableDTO updatedDesk = service.updateTable(0, "newDesc", 1l);
    assertEquals(newDesk.getDescription(), updatedDesk.getDescription());
  }

  @Test
  public void returnsOldTableWhenNewDescriptionIsNull() {
    when(repository.findById(anyLong())).thenReturn(Optional.of(newDesk));
    TableDTO updatedDesk = service.updateTable(4, null, 1l);
    assertEquals(newDesk.getDescription(), updatedDesk.getDescription());
  }

  @Test
  public void returnsAvailableTableIfDateIsGood() {
    Set<OpeningHour> openingHours = new HashSet<>();
    OpeningHour openingHour = new OpeningHour();
    openingHour.setDayOfWeek(DayOfWeek.MONDAY);
    openingHour.setFromHour(LocalTime.of(8, 0));
    openingHour.setToHour(LocalTime.of(10, 0));
    openingHours.add(openingHour);
    when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(new Restaurant(1l, "", "", "", new Address(), openingHours, desks)));
    assertTrue(service.findAvailableTables("2021-07-19 08:30", 1L).getAvailableDesks().size() > 0);
  }

  @Test
  public void notReturnsAvailableTableIfIsReserved() {
    newDesk.setState(State.RESERVED);
    Set<OpeningHour> openingHours = new HashSet<>();
    OpeningHour openingHour = new OpeningHour();
    openingHour.setDayOfWeek(DayOfWeek.MONDAY);
    openingHour.setFromHour(LocalTime.of(8, 0));
    openingHour.setToHour(LocalTime.of(10, 0));
    openingHours.add(openingHour);
    when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(new Restaurant(1L, "", "", "", new Address(), openingHours, desks)));
    assertFalse(service.findAvailableTables("2021-07-19 08:30", 1L).getAvailableDesks().size() > 0);
  }

  @Test
  public void notReturnsAvailableTableIfDayIsWrong() {
    Set<OpeningHour> openingHours = new HashSet<>();
    OpeningHour openingHour = new OpeningHour();
    openingHour.setDayOfWeek(DayOfWeek.FRIDAY);
    openingHour.setFromHour(LocalTime.of(8, 0));
    openingHour.setToHour(LocalTime.of(10, 0));
    openingHours.add(openingHour);
    when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(new Restaurant(1L, "", "", "", new Address(), openingHours, desks)));
    assertFalse(service.findAvailableTables("2021-07-19 08:30", 1L).getAvailableDesks().size() > 0);
  }

  @Test
  public void returnsTrueIfDateIsGood() {
    Set<OpeningHour> openingHours = new HashSet<>();
    OpeningHour openingHour = new OpeningHour();
    openingHour.setDayOfWeek(DayOfWeek.MONDAY);
    openingHour.setFromHour(LocalTime.of(8, 0));
    openingHour.setToHour(LocalTime.of(10, 0));
    openingHours.add(openingHour);
    Restaurant restaurant = new Restaurant(1l, "", "", "", new Address(), openingHours, desks);
    assertTrue(service.restaurantIsOpen(LocalDateTime.of(2021, 07, 19, 8, 30), restaurant));
  }

  @Test
  public void returnsFalseIfDayIsWrong() {
    Set<OpeningHour> openingHours = new HashSet<>();
    OpeningHour openingHour = new OpeningHour();
    openingHour.setDayOfWeek(DayOfWeek.FRIDAY);
    openingHour.setFromHour(LocalTime.of(8, 0));
    openingHour.setToHour(LocalTime.of(10, 0));
    openingHours.add(openingHour);
    Restaurant restaurant = new Restaurant(1l, "", "", "", new Address(), openingHours, desks);
    assertFalse(service.restaurantIsOpen(LocalDateTime.of(2021, 07, 19, 8, 30), restaurant));
  }

  @Test
  public void returnsFalseIfHourIsBeforeOpening() {
    Set<OpeningHour> openingHours = new HashSet<>();
    OpeningHour openingHour = new OpeningHour();
    openingHour.setDayOfWeek(DayOfWeek.MONDAY);
    openingHour.setFromHour(LocalTime.of(8, 0));
    openingHour.setToHour(LocalTime.of(10, 0));
    openingHours.add(openingHour);
    Restaurant restaurant = new Restaurant(1l, "", "", "", new Address(), openingHours, desks);
    assertFalse(service.restaurantIsOpen(LocalDateTime.of(2021, 07, 19, 7, 30), restaurant));
  }
}