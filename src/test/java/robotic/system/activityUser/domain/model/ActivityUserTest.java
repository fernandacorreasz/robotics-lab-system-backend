package robotic.system.activityUser.domain.model;

import org.junit.jupiter.api.Test;
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
}
