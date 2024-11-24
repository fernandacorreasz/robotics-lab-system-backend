package robotic.system.activityUser.app.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import robotic.system.activityUser.app.service.ActivityPhotoFilterService;

import java.io.IOException;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class ActivityPhotoFilterControllerTest {

    @Mock
    private ActivityPhotoFilterService activityPhotoFilterService;

    @InjectMocks
    private ActivityPhotoFilterController activityPhotoFilterController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetPhoto_Success() throws IOException {
        UUID photoId = UUID.randomUUID();
        byte[] mockPhoto = new byte[]{1, 2, 3};

        when(activityPhotoFilterService.getPhotoByFilename(photoId))
                .thenReturn(ResponseEntity.ok(mockPhoto));

        ResponseEntity<byte[]> response = activityPhotoFilterController.getPhoto(photoId);

        assertNotNull(response);
        assertNotNull(response.getBody());
        verify(activityPhotoFilterService).getPhotoByFilename(photoId);
    }

    @Test
    void testGetPhoto_NotFound() throws IOException {
        UUID photoId = UUID.randomUUID();

        when(activityPhotoFilterService.getPhotoByFilename(photoId))
                .thenThrow(new IOException("Photo not found"));

        try {
            activityPhotoFilterController.getPhoto(photoId);
        } catch (IOException e) {
            assertNotNull(e);
            assertEquals("Photo not found", e.getMessage());
        }

        verify(activityPhotoFilterService).getPhotoByFilename(photoId);
    }
}
