package peaksoft.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.entities.User;
import peaksoft.exceptions.NotFoundException;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    @Query("select u from User u where  u.email = :email")
    Optional<User> findByEmail(String email);


    default User getByEmail(String email) {
        return findByEmail(email).orElseThrow(() ->
                new NotFoundException("User with email: " + email + " not found!"));
    }
    Page<User> findAllByRestaurantId(Long restaurantId, Pageable pageable);

}