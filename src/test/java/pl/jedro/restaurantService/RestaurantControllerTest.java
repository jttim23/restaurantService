package pl.jedro.restaurantService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pl.jedro.restaurantService.model.DTOs.RestaurantDTO;
import pl.jedro.restaurantService.model.Restaurant;
import pl.jedro.restaurantService.services.DeskService;
import pl.jedro.restaurantService.services.OpeningHoursService;
import pl.jedro.restaurantService.services.RestaurantService;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RestaurantController.class)
class RestaurantControllerTest {
  @MockBean
  private RestaurantService service;
  @MockBean
  private DeskService deskService;
  @MockBean
  private OpeningHoursService openingHoursService;
  @Autowired
  MockMvc mockMvc;
  @Autowired
  ObjectMapper mapper;

  @Test
  public void getRestaurant() throws Exception {
    Restaurant restaurant = new Restaurant();
    restaurant.setId(1L);
    restaurant.setEmailAddress("test@gmail.com");
    when(service.findRestaurantById(anyLong())).thenReturn(restaurant);
    mockMvc.perform(get("/v1/restaurants/1").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id", equalTo(1))).andExpect(status().isOk());
  }

  @Test
  public void createNewRestaurant() throws Exception {
    RestaurantDTO dto = new RestaurantDTO();
    dto.setEmailAddress("test");
    when(service.saveNewRestaurant(any())).thenReturn(dto);

    mockMvc.perform((post("/v1/restaurants/new").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto)))).andExpect(status()
        .isCreated()).andExpect(jsonPath("$.emailAddress", equalTo("test")));


  }
}