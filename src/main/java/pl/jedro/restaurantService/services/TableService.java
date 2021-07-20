package pl.jedro.restaurantService.services;

import org.springframework.stereotype.Service;
import pl.jedro.restaurantService.model.Table;
import pl.jedro.restaurantService.repositories.TableRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class
TableService {
  private TableRepository tableRepository;

  public TableService(TableRepository tableRepository) {
    this.tableRepository = tableRepository;
  }

  public List<Table> findAllTables(Long restaurantId) {
    if (restaurantId == null) {
      throw new IllegalArgumentException();
    }
    return tableRepository.findAll().stream().filter(table -> table.getRestaurantId() == restaurantId).collect(Collectors.toList());
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


}
