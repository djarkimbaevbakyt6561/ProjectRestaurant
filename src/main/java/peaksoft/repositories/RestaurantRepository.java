package peaksoft.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import peaksoft.entities.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
}