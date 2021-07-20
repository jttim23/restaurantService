package pl.jedro.restaurantService.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.jedro.restaurantService.model.Table;
@Repository
public interface TableRepository extends JpaRepository<Table,Long> {
}
