package pl.jedro.restaurantService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.jedro.restaurantService.model.Address;
import pl.jedro.restaurantService.model.OpeningHour;
import pl.jedro.restaurantService.model.Restaurant;
import pl.jedro.restaurantService.model.Table;
import pl.jedro.restaurantService.services.OpeningHoursService;
import pl.jedro.restaurantService.services.RestaurantService;
import pl.jedro.restaurantService.services.TableService;

import java.util.List;
import java.util.Set;


@RestController
public class RestaurantController {
  private RestaurantService restaurantService;
  private TableService tableService;
private OpeningHoursService openingHoursService;

  public RestaurantController(RestaurantService restaurantService, TableService tableService, OpeningHoursService openingHoursService) {
    this.restaurantService = restaurantService;
    this.tableService = tableService;
    this.openingHoursService = openingHoursService;
  }

  @GetMapping("/v1/restaurants/{id}")
  public Restaurant getRestaurant(@PathVariable Long id) {
    return restaurantService.findRestaurantById(id);
  }

  @PostMapping("/v1/restaurants/new")
  @ResponseStatus(HttpStatus.CREATED)
  public Restaurant postRestaurant(@RequestBody Restaurant restaurant) {
    return restaurantService.saveNewRestaurant(restaurant);
  }

  @GetMapping("/v1/restaurants/{restaurantId}/tables/all")
  public List<Table> getAllTables(@PathVariable Long restaurantId) {
    return tableService.findAllTables(restaurantId);
  }

  @PostMapping("/v1/restaurants/{restaurantId}/tables/new")
  public Restaurant postTable(@RequestBody Table newTable, @PathVariable Long restaurantId) {
    return restaurantService.saveNewTable(newTable, restaurantId);
  }

  @PostMapping("/v1/restaurants/{restaurantId}/newTables")
  public Restaurant postTable(@RequestBody List<Table> newTables, @PathVariable Long restaurantId) {
    return restaurantService.saveNewTables(newTables, restaurantId);
  }

  @PutMapping("/v1/restaurants/{*}/tables/update/{tableId}")
  public Table putTable(@RequestParam Integer peopleQuantity, @RequestParam String description, @PathVariable Long tableId) {
    return tableService.updateTable(peopleQuantity, description, tableId);
  }

  @GetMapping("/v1/restaurants/{restaurantId}/address")
  public Address getAddress(@PathVariable Long restaurantId) {
    return restaurantService.findAddress(restaurantId);
  }

  @PostMapping("/v1/restaurants/{restaurantId}/newAddress")
  public Restaurant postAddress(@RequestBody Address address, @PathVariable Long restaurantId) {
    return restaurantService.saveNewAddress(address, restaurantId);
  }

  @PutMapping("/v1/restaurants/{restaurantId}/address/update")
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
  public Restaurant postOpeningHours(@RequestBody Set<OpeningHour> openingHours, @PathVariable Long restaurantId) {
    return restaurantService.saveNewOpeningHours(openingHours, restaurantId);
  }

  @PutMapping("/v1/restaurants/{*}/openingHours/update/{openingHoursId}")
  public OpeningHour putOpeningHours(@RequestParam String dayOfWeek, @RequestParam String fromHour, @RequestParam String toHour,
                                     @PathVariable Long openingHoursId) {
    return openingHoursService.updateOpeningHours(dayOfWeek,fromHour,toHour,openingHoursId);
  }


}
