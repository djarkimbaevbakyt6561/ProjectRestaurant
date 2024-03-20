package peaksoft.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.entities.Subcategory;

import java.util.List;

public interface SubcategoryRepository extends JpaRepository<Subcategory, Long> {
    @Query("select c.subcategories from Category c where c.id = :categoryId order by c.name")
    List<Subcategory> findAllByCategoryId(Long categoryId);

}