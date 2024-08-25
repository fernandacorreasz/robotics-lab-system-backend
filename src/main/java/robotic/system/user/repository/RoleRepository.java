package robotic.system.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import robotic.system.user.domain.model.Role;
import java.util.UUID;
public interface RoleRepository extends JpaRepository<Role, UUID> {
    Role findByNameRole(String nameRole);

}
