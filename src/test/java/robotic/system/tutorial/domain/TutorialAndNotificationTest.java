package robotic.system.tutorial.domain;

import org.junit.jupiter.api.Test;
import robotic.system.inventory.domain.Component;
import robotic.system.notification.domain.model.Notification;
import robotic.system.tutorial.domain.model.Tutorial;
import robotic.system.tutorial.domain.model.TutorialBuilder;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TutorialAndNotificationTest {

    @Test
    void testTutorialAndBuilder() {
        String title = "Tutorial Title";
        String content = "This is a sample tutorial content.";
        Set<Component> components = new HashSet<>();

        Tutorial tutorial = new TutorialBuilder()
                .withTitle(title)
                .withContent(content)
                .withComponents(components)
                .build();

        assertNotNull(tutorial.getId(), "Tutorial ID should not be null");
        assertEquals(title, tutorial.getTitle(), "Title should match");
        assertEquals(content, tutorial.getContent(), "Content should match");
        assertEquals(components, tutorial.getComponents(), "Components should match");
    }

    @Test
    void testDefaultConstructor_Tutorial() {

        Tutorial tutorial = new Tutorial();

        assertNotNull(tutorial.getTutorialId(), "Default tutorialId should not be null");
        assertNull(tutorial.getTitle(), "Default title should be null");
        assertNull(tutorial.getContent(), "Default content should be null");
        assertNull(tutorial.getComponents(), "Default components should be null");
    }


    @Test
    void testNotification() {
        String message = "This is a notification";
        String recipientEmail = "user@example.com";
        Date sentAt = new Date();


        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setRecipientEmail(recipientEmail);
        notification.setSentAt(sentAt);

        assertNotNull(notification.getNotificationId(), "Notification unique ID should not be null");
        assertEquals(message, notification.getMessage(), "Message should match");
        assertEquals(recipientEmail, notification.getRecipientEmail(), "Recipient email should match");
        assertEquals(sentAt, notification.getSentAt(), "SentAt should match");
        assertFalse(notification.isRead(), "Default read status should be false");
    }

    @Test
    void testDefaultConstructor_Notification() {
        Notification notification = new Notification();
        assertNotNull(notification.getNotificationId(), "Default notificationId should not be null");
        assertNull(notification.getMessage(), "Default message should be null");
        assertNull(notification.getRecipientEmail(), "Default recipient email should be null");
        assertNull(notification.getSentAt(), "Default sentAt should be null");
        assertFalse(notification.isRead(), "Default read status should be false");
    }
}