package pl.jedro.restaurantService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.jedro.restaurantService.model.Address;
import pl.jedro.restaurantService.model.DTOs.AvailableTablesDTO;
import pl.jedro.restaurantService.model.DTOs.ReservationListDTO;
import pl.jedro.restaurantService.model.DTOs.RestaurantDTO;
import pl.jedro.restaurantService.model.DTOs.TableDTO;
import pl.jedro.restaurantService.model.OpeningHour;
import pl.jedro.restaurantService.model.Restaurant;
import pl.jedro.restaurantService.services.DeskService;
import pl.jedro.restaurantService.services.OpeningHoursService;
import pl.jedro.restaurantService.services.RestaurantService;

import java.util.List;
import java.util.Set;


@RestController
public class RestaurantController {
  private RestaurantService restaurantService;
  private DeskService deskService;
  private OpeningHoursService openingHoursService;

  public RestaurantController(RestaurantService restaurantService, DeskService deskService, OpeningHoursService openingHoursService) {
    this.restaurantService = restaurantService;
    this.deskService = deskService;
    this.openingHoursService = openingHoursService;
  }

  @GetMapping("/v1/restaurants/{id}")
  public Restaurant getRestaurant(@PathVariable Long id) {
    return restaurantService.findRestaurantById(id);
  }
  @GetMapping("/v1/restaurants")
  public Restaurant getRestaurantFoo() {
    return new Restaurant();
  }

  @PostMapping(value = "/v1/restaurants/new")
  @ResponseStatus(HttpStatus.CREATED)
  public RestaurantDTO postRestaurant(@RequestBody RestaurantDTO restaurant) {
    return restaurantService.saveNewRestaurant(restaurant);
  }

  @GetMapping("/v1/restaurants/{restaurantId}/tables/all")
  public List<TableDTO> getAllTables(@PathVariable Long restaurantId) {
    return deskService.findAllTables(restaurantId);
  }

  @GetMapping("/v1/restaurants/{restaurantId}/tables/available")
  public AvailableTablesDTO getAvailableTables(@RequestParam String date, @PathVariable Long restaurantId) {
    return deskService.findAvailableTables(date, restaurantId);
  }

  @PostMapping("/v1/restaurants/{restaurantId}/tables/new")
  @ResponseStatus(HttpStatus.CREATED)
  public Restaurant postTable(@RequestBody TableDTO newTable, @PathVariable Long restaurantId) {
    return restaurantService.saveNewTable(newTable, restaurantId);
  }

  @PostMapping("/v1/restaurants/{restaurantId}/newTables")
  @ResponseStatus(HttpStatus.CREATED)
  public Restaurant postTable(@RequestBody List<TableDTO> newTables, @PathVariable Long restaurantId) {
    return restaurantService.saveNewTables(newTables, restaurantId);
  }

  @PutMapping("/v1/restaurants/{*}/tables/update/{tableId}")
  @ResponseStatus(HttpStatus.CREATED)
  public TableDTO putTable(@RequestParam Integer peopleQuantity, @RequestParam String description, @PathVariable Long tableId) {
    return deskService.updateTable(peopleQuantity, description, tableId);
  }

  @PutMapping("/v1/restaurants/{*}/tables/setState/{tableId}")
  @ResponseStatus(HttpStatus.CREATED)
  public TableDTO putTableState(@RequestParam String state, @PathVariable Long tableId) {
    return deskService.setTableState(state, tableId);
  }

  @GetMapping("/v1/restaurants/{restaurantId}/address")
  public Address getAddress(@PathVariable Long restaurantId) {
    return restaurantService.findAddress(restaurantId);
  }

  @PostMapping("/v1/restaurants/{restaurantId}/newAddress")
  @ResponseStatus(HttpStatus.CREATED)
  public Restaurant postAddress(@RequestBody Address address, @PathVariable Long restaurantId) {
    return restaurantService.saveNewAddress(address, restaurantId);
  }

  @PutMapping("/v1/restaurants/{restaurantId}/address/update")
  @ResponseStatus(HttpStatus.CREATED)
  public Address putAddress(@RequestParam String street,
                            @RequestParam String apartmentNumber,
                            @RequestParam String zipCode,
                            @RequestParam String city, @PathVariable Long restaurantId) {
    return restaurantService.updateAddress(street, apartmentNumber, zipCode, city, restaurantId);
  }

  @GetMapping("/v1/restaurants/{restaurantId}/openingHours")
  public Set<OpeningHour> getOpeningHours(@PathVariable Long restaurantId) {
    return restaurantService.findOpeningHours(restaurantId);
  }

  @PostMapping("/v1/restaurants/{restaurantId}/newOpeningHours")
  @ResponseStatus(HttpStatus.CREATED)
  public Restaurant postOpeningHours(@RequestBody Set<OpeningHour> openingHours, @PathVariable Long restaurantId) {
    return restaurantService.saveNewOpeningHours(openingHours, restaurantId);
  }

  @PutMapping("/v1/restaurants/{*}/openingHours/update/{openingHoursId}")
  @ResponseStatus(HttpStatus.CREATED)
  public OpeningHour putOpeningHours(@RequestParam String dayOfWeek, @RequestParam String fromHour, @RequestParam String toHour,
                                     @PathVariable Long openingHoursId) {
    return openingHoursService.updateOpeningHours(dayOfWeek, fromHour, toHour, openingHoursId);
  }

  @GetMapping("v1/restaurants/{restaurantId}/ourReservations")
  public ReservationListDTO getAllReservations(@PathVariable Long restaurantId) {
    return restaurantService.getAllReservations(restaurantId);
  }


}
