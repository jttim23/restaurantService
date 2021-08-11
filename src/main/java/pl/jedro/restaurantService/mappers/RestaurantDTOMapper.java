package pl.jedro.restaurantService.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pl.jedro.restaurantService.model.DTOs.RestaurantDTO;
import pl.jedro.restaurantService.model.Restaurant;

@Mapper
public interface RestaurantDTOMapper {
  RestaurantDTOMapper INSTANCE = Mappers.getMapper(RestaurantDTOMapper.class);

  Restaurant restaurantDTOtoRestaurant(RestaurantDTO restaurantDTO);

  RestaurantDTO restaurantToRestaurantDTO(Restaurant restaurantD);
}
