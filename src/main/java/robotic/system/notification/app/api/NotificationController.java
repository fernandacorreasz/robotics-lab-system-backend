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

    // Endpoint para marcar uma notificação como lida (opcional)
    @PutMapping("/{id}/mark-as-read")
    public ResponseEntity<Void> markNotificationAsRead(@PathVariable UUID id) {
        notificationService.markAsRead(id);
        return ResponseEntity.noContent().build();
    }
}
