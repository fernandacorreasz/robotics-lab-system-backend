package robotic.system.notification.app.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import robotic.system.notification.app.service.NotificationService;
import robotic.system.notification.domain.model.Notification;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    // Endpoint para buscar todas as notificações de um usuário pelo e-mail
    @GetMapping("/user/{email}")
    public ResponseEntity<List<Notification>> getNotificationsForUser(@PathVariable String email) {
        List<Notification> notifications = notificationService.getNotificationsByUserEmail(email);
        return ResponseEntity.ok(notifications);
    }

      // Endpoint para excluir uma notificação específica de um usuário
      @DeleteMapping("/user/{email}/notification/{notificationId}")
      public ResponseEntity<Void> deleteNotificationForUser(
              @PathVariable String email,
              @PathVariable UUID notificationId) {
          boolean deleted = notificationService.deleteNotificationByUserEmailAndId(email, notificationId);
          return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
      }

}
