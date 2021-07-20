package pl.jedro.restaurantService.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.jedro.restaurantService.model.OpeningHour;
@Repository
public interface HoursRepository extends JpaRepository<OpeningHour,Long> {
}
