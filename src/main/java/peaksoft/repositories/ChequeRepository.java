package peaksoft.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import peaksoft.entities.Cheque;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface ChequeRepository extends JpaRepository<Cheque, Long> {

    @Query("SELECT COUNT(c) FROM Cheque c WHERE c.user.id = :userId AND DATE(c.createdAt) = :date")
    Long countByUserIdAndCreatedAt(Long userId, LocalDate date);

    @Query("SELECT AVG(c.priceAverage) FROM Cheque c WHERE c.user.restaurant.id = :restaurantId AND DATE(c.createdAt) = :date")
    BigDecimal findAvgTotalPriceByUserRestaurantId(Long restaurantId, LocalDate date);
}