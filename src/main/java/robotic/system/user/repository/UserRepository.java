package robotic.system.user.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import robotic.system.user.domain.model.Users;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<Users, UUID> {
    Users findByEmail(String email);
    Optional<Users> findByName(String name);
    
}
