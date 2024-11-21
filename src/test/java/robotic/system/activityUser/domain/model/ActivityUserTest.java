package robotic.system.activityUser.domain.model;

import org.junit.jupiter.api.Test;
import robotic.system.activityUser.domain.dto.ActivityUserDTO;
import robotic.system.activityUser.domain.dto.ActivityWithCommentsDTO;
import robotic.system.activityUser.domain.dto.CommentDTO;
import robotic.system.activityUser.domain.en.ActivityStatus;
import robotic.system.inventory.domain.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ActivityUserTest {

    @Test
    void testActivityUserBuilder() {
        String title = "Test Activity";
        String description = "Activity Description";
        Integer timeSpent = 60;
        Date startDate = new Date();
        Date endDate = new Date();
        String userCode = "USER123";
        List<ActivityPhoto> photos = new ArrayList<>();
        List<Component> componentsUsed = new ArrayList<>();

        ActivityUser user = new ActivityUserBuilder()
                .withActivityTitle(title)
                .withActivityDescription(description)
                .withTimeSpent(timeSpent)
                .withStartDate(startDate)
                .withEndDate(endDate)
                .withUserCode(userCode)
                .withPhotos(photos)
                .withComponentsUsed(componentsUsed)
                .build();

        assertNotNull(user.getId(), "ID should not be null");
        assertEquals(title, user.getActivityTitle(), "Title should match");
        assertEquals(description, user.getActivityDescription(), "Description should match");
        assertEquals(timeSpent, user.getTimeSpent(), "Time spent should match");
        assertEquals(startDate, user.getStartDate(), "Start date should match");
        assertEquals(endDate, user.getEndDate(), "End date should match");
        assertEquals(userCode, user.getUserCode(), "User code should match");
        assertEquals(photos, user.getPhotos(), "Photos list should match");
        assertEquals(componentsUsed, user.getComponentsUsed(), "Components used should match");
    }

    @Test
    void testDefaultConstructor() {
        ActivityUser user = new ActivityUser();
        assertNull(user.getActivityTitle(), "Activity title should be null initially");
        assertNull(user.getPhotos(), "Photos should be null initially");
    }

    @Test
    void testActivityUserDTO() {
        UUID id = UUID.randomUUID();
        String title = "Test Activity";
        String description = "Activity Description";
        Integer timeSpent = 60;
        Date startDate = new Date();
        Date endDate = new Date();
        UUID userId = UUID.randomUUID();
        String userEmail = "test@example.com";

        ActivityUserDTO dto = new ActivityUserDTO(id, title, description, ActivityStatus.IN_PROGRESS,
                timeSpent, startDate, endDate, userId, userEmail);

        assertEquals(id, dto.getId());
        assertEquals(title, dto.getActivityTitle());
        assertEquals(description, dto.getActivityDescription());
        assertEquals(ActivityStatus.IN_PROGRESS, dto.getActivityStatus());
        assertEquals(timeSpent, dto.getTimeSpent());
        assertEquals(startDate, dto.getStartDate());
        assertEquals(endDate, dto.getEndDate());
        assertEquals(userId, dto.getUserId());
        assertEquals(userEmail, dto.getUserEmail());
    }

    @Test
    void testActivityWithCommentsDTO() {
        UUID id = UUID.randomUUID();
        String title = "Test Activity with Comments";
        String description = "Description for activity with comments";
        Integer timeSpent = 90;
        Date startDate = new Date();
        Date endDate = new Date();
        UUID userId = UUID.randomUUID();
        String userEmail = "user@example.com";
        List<CommentDTO> comments = List.of(
                new CommentDTO(UUID.randomUUID(), "Comment 1", new Date()),
                new CommentDTO(UUID.randomUUID(), "Comment 2", new Date())
        );

        ActivityWithCommentsDTO dto = new ActivityWithCommentsDTO(id, title, description, ActivityStatus.COMPLETED,
                timeSpent, startDate, endDate, userId, userEmail, comments);

        assertEquals(id, dto.getId());
        assertEquals(title, dto.getActivityTitle());
        assertEquals(description, dto.getActivityDescription());
        assertEquals(ActivityStatus.COMPLETED, dto.getActivityStatus());
        assertEquals(timeSpent, dto.getTimeSpent());
        assertEquals(startDate, dto.getStartDate());
        assertEquals(endDate, dto.getEndDate());
        assertEquals(userId, dto.getUserId());
        assertEquals(userEmail, dto.getUserEmail());
        assertEquals(comments, dto.getComments());
    }

    @Test
    void testCommentDTO() {
        UUID id = UUID.randomUUID();
        String text = "Sample comment text";
        Date createdDate = new Date();

        CommentDTO dto = new CommentDTO(id, text, createdDate);

        assertEquals(id, dto.getId());
        assertEquals(text, dto.getText());
        assertEquals(createdDate, dto.getCreatedDate());
    }
}
