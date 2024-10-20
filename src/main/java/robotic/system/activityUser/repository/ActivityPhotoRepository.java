package robotic.system.activityUser.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import robotic.system.activityUser.domain.model.ActivityPhoto;
import java.util.UUID;

public interface ActivityPhotoRepository extends JpaRepository<ActivityPhoto, UUID> {
}
