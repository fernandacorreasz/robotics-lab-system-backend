package robotic.system.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import robotic.system.notification.domain.model.Notification;

import java.util.List;
import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {
    
    List<Notification> findByRecipientEmail(String email);
}
