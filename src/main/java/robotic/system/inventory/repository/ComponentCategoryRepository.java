package robotic.system.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import robotic.system.inventory.domain.ComponentCategory;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ComponentCategoryRepository extends JpaRepository<ComponentCategory, UUID> {
    
    Optional<ComponentCategory> findByCategoryName(String categoryName);

    @Query("SELECT DISTINCT c FROM ComponentCategory c LEFT JOIN FETCH c.subCategories")
    List<ComponentCategory> findAllCategoriesWithSubcategories();
}