package pl.jedro.restaurantService.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.jedro.restaurantService.model.Address;
@Repository
public interface AddressRepository extends JpaRepository<Address,Long> {
}
