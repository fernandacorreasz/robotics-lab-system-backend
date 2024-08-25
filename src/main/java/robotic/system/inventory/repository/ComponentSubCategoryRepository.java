package robotic.system.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import robotic.system.inventory.domain.model.ComponentSubCategory;

import java.util.UUID;

public interface ComponentSubCategoryRepository extends JpaRepository<ComponentSubCategory, UUID> {
   
}
