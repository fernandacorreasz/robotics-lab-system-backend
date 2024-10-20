package robotic.system.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import robotic.system.inventory.domain.Component;

import java.util.UUID;

public interface ComponentRepository extends JpaRepository<Component, UUID> {
}
