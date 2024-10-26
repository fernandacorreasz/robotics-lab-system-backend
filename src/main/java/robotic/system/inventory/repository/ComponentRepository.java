package robotic.system.inventory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import robotic.system.inventory.domain.Component;
import robotic.system.inventory.domain.dto.ComponentDTO;
import robotic.system.inventory.domain.dto.ComponentWithAssociationsDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ComponentRepository extends JpaRepository<Component, UUID>, JpaSpecificationExecutor<Component> {

    @Query("SELECT c.id as id, c.componentId as componentId, c.name as name, c.serialNumber as serialNumber, c.description as description, c.quantity as quantity FROM Component c")
    Page<ComponentDTO> findAllProjected(Pageable pageable);

    @Query("SELECT new robotic.system.inventory.domain.dto.ComponentWithAssociationsDTO(c.id, c.componentId, c.name, c.serialNumber, c.description, c.quantity, " +
           "sc.id, sc.subCategoryName) " +
           "FROM Component c " +
           "LEFT JOIN c.subCategory sc")
    Page<ComponentWithAssociationsDTO> findAllWithAssociations(Pageable pageable);

    Optional<Component> findBySerialNumber(String serialNumber);

    Optional<Component> findByName(String name);

    @Query("SELECT c FROM Component c WHERE c.subCategory.id = :subCategoryId")
    List<Component> findBySubCategoryId(UUID subCategoryId);
}
