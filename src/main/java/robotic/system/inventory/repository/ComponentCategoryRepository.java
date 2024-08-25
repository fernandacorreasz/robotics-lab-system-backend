package robotic.system.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import robotic.system.inventory.domain.model.ComponentCategory;

import java.util.UUID;

public interface ComponentCategoryRepository extends JpaRepository<ComponentCategory, UUID> {

}