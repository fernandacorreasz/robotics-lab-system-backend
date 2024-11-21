package robotic.system.notification.app.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import robotic.system.loanComponent.domain.model.LoanComponent;
import robotic.system.notification.domain.model.Notification;
import robotic.system.notification.repository.NotificationRepository;
import robotic.system.user.domain.model.Users;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

class NotificationServiceTest {

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationService notificationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetNotificationsByUserEmail() {
        // Arrange
        String email = "user@example.com";
        List<Notification> mockNotifications = List.of(mock(Notification.class), mock(Notification.class));
        when(notificationRepository.findByRecipientEmail(email)).thenReturn(mockNotifications);

        // Act
        List<Notification> notifications = notificationService.getNotificationsByUserEmail(email);

        // Assert
        assertEquals(2, notifications.size());
        verify(notificationRepository).findByRecipientEmail(email);
    }

    @Test
    void testDeleteNotificationByUserEmailAndId_Success() {

        String email = "user@example.com";
        UUID notificationId = UUID.randomUUID();
        Notification notification = new Notification();
        notification.setRecipientEmail(email);

        when(notificationRepository.findById(notificationId)).thenReturn(Optional.of(notification));


        boolean result = notificationService.deleteNotificationByUserEmailAndId(email, notificationId);

        assertTrue(result);
        verify(notificationRepository).delete(notification);
    }

    @Test
    void testDeleteNotificationByUserEmailAndId_Failure() {

        String email = "user@example.com";
        UUID notificationId = UUID.randomUUID();
        when(notificationRepository.findById(notificationId)).thenReturn(Optional.empty());

        boolean result = notificationService.deleteNotificationByUserEmailAndId(email, notificationId);

        assertFalse(result);
        verify(notificationRepository, never()).delete(any(Notification.class));
    }
}
