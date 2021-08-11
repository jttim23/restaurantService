package pl.jedro.restaurantService.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pl.jedro.restaurantService.model.DTOs.TableDTO;
import pl.jedro.restaurantService.model.Desk;

@Mapper
public interface TableDTOMapper {
  TableDTOMapper INSTANCE = Mappers.getMapper(TableDTOMapper.class);

  TableDTO deskToTableDTO(Desk desk);

  Desk tableDTOtoDesk(TableDTO dto);
}
