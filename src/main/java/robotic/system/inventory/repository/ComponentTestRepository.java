package robotic.system.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import robotic.system.inventory.domain.ComponentTest;

import java.util.UUID;

public interface ComponentTestRepository extends JpaRepository<ComponentTest, UUID> {
  
}
