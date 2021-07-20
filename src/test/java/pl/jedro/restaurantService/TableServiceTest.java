package pl.jedro.restaurantService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.jedro.restaurantService.model.Restaurant;
import pl.jedro.restaurantService.model.Table;
import pl.jedro.restaurantService.repositories.TableRepository;
import pl.jedro.restaurantService.services.TableService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class TableServiceTest {
  @Mock
  TableRepository repository;
  TableService service;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    service = new TableService(repository);
  }

  @Test
  public void returnsOldTableWhenNewDescriptionIsEmpty() {
    Table newTable = new Table(1l, 4, "test", new Restaurant());
    when(repository.findById(anyLong())).thenReturn(Optional.of(newTable));
    Table updatedTable = service.updateTable(4, "", 1l);
    assertEquals(newTable.getDescription(), updatedTable.getDescription());
  }

  @Test
  public void returnsOldTableWhenNewPeopleLessThan1() {
    Table newTable = new Table(1l, 4, "test", new Restaurant());
    when(repository.findById(anyLong())).thenReturn(Optional.of(newTable));
    Table updatedTable = service.updateTable(0, "newDesc", 1l);
    assertEquals(newTable.getDescription(), updatedTable.getDescription());
  }

  @Test
  public void returnsOldTableWhenNewDescriptionIsNull() {
    Table newTable = new Table(1l, 4, "", new Restaurant());
    when(repository.findById(anyLong())).thenReturn(Optional.of(newTable));
    Table updatedTable = service.updateTable(4, null, 1l);
    assertEquals(newTable.getDescription(), updatedTable.getDescription());
  }

}