package robotic.system.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import robotic.system.inventory.domain.ComponentSubCategory;

import java.util.UUID;

public interface ComponentSubCategoryRepository extends JpaRepository<ComponentSubCategory, UUID> {
   
}
