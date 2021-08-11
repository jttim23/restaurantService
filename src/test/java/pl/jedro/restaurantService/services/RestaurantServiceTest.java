package pl.jedro.restaurantService.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;
import pl.jedro.restaurantService.mappers.RestaurantDTOMapper;
import pl.jedro.restaurantService.model.DTOs.RestaurantDTO;
import pl.jedro.restaurantService.model.Restaurant;
import pl.jedro.restaurantService.repositories.RestaurantRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

class RestaurantServiceTest {
  private RestaurantService restaurantService;
  @Mock
  RestTemplate restTemplate;
  @Mock
  RestaurantRepository restaurantRepository;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    restaurantService = new RestaurantService(restaurantRepository, restTemplate);
  }

  @Test
  public void test() {
    RestaurantDTO dto = new RestaurantDTO();

    dto.setEmailAddress("test");
    dto.setPhoneNumber("1234");
    dto.setPassword("test");
    RestaurantDTOMapper mapper = RestaurantDTOMapper.INSTANCE;
    Restaurant restaurant = mapper.restaurantDTOtoRestaurant(dto);
    assertEquals(dto.getPhoneNumber(), restaurant.getPhoneNumber());
  }

  @Test
  public void createsNewRestaurant() {
    RestaurantDTO dto = new RestaurantDTO();
    dto.setEmailAddress("test");
    dto.setPhoneNumber("1234");
    dto.setPassword("test");
    Restaurant restaurant = new Restaurant();
    restaurant.setEmailAddress(dto.getEmailAddress());
    restaurant.setPassword(dto.getPassword());
    restaurant.setPhoneNumber(dto.getPhoneNumber());
    when(restaurantRepository.save(any(Restaurant.class))).thenReturn(restaurant);
    RestaurantDTO savedDTO = restaurantService.saveNewRestaurant(dto);
    assertEquals(savedDTO.getEmailAddress(), restaurant.getEmailAddress());
    assertEquals(savedDTO.getPhoneNumber(), restaurant.getPhoneNumber());
  }
}