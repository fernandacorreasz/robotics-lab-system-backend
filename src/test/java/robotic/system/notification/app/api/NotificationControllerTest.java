package robotic.system.notification.app.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import robotic.system.notification.app.service.NotificationService;
import robotic.system.notification.domain.model.Notification;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NotificationControllerTest {

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private NotificationController notificationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetNotificationsForUser() {
        String email = "user@example.com";
        List<Notification> mockNotifications = List.of(mock(Notification.class), mock(Notification.class));
        when(notificationService.getNotificationsByUserEmail(email)).thenReturn(mockNotifications);

        ResponseEntity<List<Notification>> response = notificationController.getNotificationsForUser(email);

        assertEquals(200, response.getStatusCodeValue(), "Response status should be 200");
        assertEquals(2, response.getBody().size(), "Should return 2 notifications");
        verify(notificationService).getNotificationsByUserEmail(email);
    }

    @Test
    void testDeleteNotificationForUser_Success() {
        // Arrange
        String email = "user@example.com";
        UUID notificationId = UUID.randomUUID();
        when(notificationService.deleteNotificationByUserEmailAndId(email, notificationId)).thenReturn(true);

        ResponseEntity<Void> response = notificationController.deleteNotificationForUser(email, notificationId);

        assertEquals(204, response.getStatusCodeValue(), "Response status should be 204");
        verify(notificationService).deleteNotificationByUserEmailAndId(email, notificationId);
    }

    @Test
    void testDeleteNotificationForUser_NotFound() {

        String email = "user@example.com";
        UUID notificationId = UUID.randomUUID();
        when(notificationService.deleteNotificationByUserEmailAndId(email, notificationId)).thenReturn(false);

        ResponseEntity<Void> response = notificationController.deleteNotificationForUser(email, notificationId);

        assertEquals(404, response.getStatusCodeValue(), "Response status should be 404");
        verify(notificationService).deleteNotificationByUserEmailAndId(email, notificationId);
    }
}
