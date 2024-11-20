package robotic.system.activityUser.domain.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ActivityPhotoTest {

    @Test
    void testActivityPhotoBuilder() {
        byte[] imageFile = "sampleImage".getBytes();
        String filename = "photo.jpg";
        List<ActivityUser> activities = new ArrayList<>();

        ActivityPhoto photo = new ActivityPhotoBuilder()
                .withImageFile(imageFile)
                .withFilename(filename)
                .withActivities(activities)
                .build();

        assertNotNull(photo.getId(), "ID should not be null");
        assertArrayEquals(imageFile, photo.getImageFile(), "ImageFile should match");
        assertEquals(filename, photo.getFilename(), "Filename should match");
        assertEquals(activities, photo.getActivities(), "Activities list should match");
    }

    @Test
    void testDefaultConstructor() {
        ActivityPhoto photo = new ActivityPhoto();
        assertNull(photo.getImageFile(), "ImageFile should be null initially");
        assertNull(photo.getFilename(), "Filename should be null initially");
        assertNull(photo.getActivities(), "Activities should be null initially");
    }
}
