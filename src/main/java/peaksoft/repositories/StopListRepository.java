package peaksoft.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import peaksoft.entities.StopList;

public interface StopListRepository extends JpaRepository<StopList, Long> {
}