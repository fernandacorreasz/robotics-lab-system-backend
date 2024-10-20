package robotic.system.activityUser.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import robotic.system.activityUser.domain.model.ActivityUser;
import java.util.UUID;

public interface ActivityUserRepository extends JpaRepository<ActivityUser, UUID> {
}
