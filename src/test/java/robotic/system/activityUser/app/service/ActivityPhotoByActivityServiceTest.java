package robotic.system.activityUser.app.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import robotic.system.activityUser.domain.model.ActivityPhoto;
import robotic.system.activityUser.domain.model.ActivityUser;
import robotic.system.activityUser.repository.ActivityUserRepository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ActivityPhotoByActivityServiceTest {

    @Mock
    private ActivityUserRepository activityUserRepository;

    @InjectMocks
    private ActivityPhotoByActivityService activityPhotoByActivityService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetPhotoByActivityId_ActivityNotFound() throws IOException {
        UUID activityId = UUID.randomUUID();
        when(activityUserRepository.findById(activityId)).thenReturn(Optional.empty());

        ResponseEntity<?> response = activityPhotoByActivityService.getPhotoByActivityId(activityId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Atividade não encontrada.", response.getBody());
        verify(activityUserRepository).findById(activityId);
    }

    @Test
    void testGetPhotoByActivityId_NoPhotosFound() throws IOException {
        UUID activityId = UUID.randomUUID();
        ActivityUser activityUser = new ActivityUser();
        activityUser.setPhotos(new ArrayList<>());

        when(activityUserRepository.findById(activityId)).thenReturn(Optional.of(activityUser));

        ResponseEntity<?> response = activityPhotoByActivityService.getPhotoByActivityId(activityId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Nenhuma foto associada a essa atividade.", response.getBody());
        verify(activityUserRepository).findById(activityId);
    }

    @Test
    void testGetPhotoByActivityId_PhotoFound() throws IOException {
        UUID activityId = UUID.randomUUID();
        String filename = "test.jpg";
        byte[] zipBytes = createMockZipWithFile(filename, "Mock file content".getBytes());

        ActivityPhoto activityPhoto = new ActivityPhoto();
        activityPhoto.setFilename(filename);
        activityPhoto.setImageFile(zipBytes);

        List<ActivityPhoto> photos = new ArrayList<>();
        photos.add(activityPhoto);

        ActivityUser activityUser = new ActivityUser();
        activityUser.setPhotos(photos);

        when(activityUserRepository.findById(activityId)).thenReturn(Optional.of(activityUser));

        ResponseEntity<?> response = activityPhotoByActivityService.getPhotoByActivityId(activityId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        HttpHeaders headers = response.getHeaders();
        assertEquals("image/jpeg", headers.getContentType().toString());
        assertEquals("inline; filename=\"test.jpg\"", headers.get("Content-Disposition").get(0));
        verify(activityUserRepository).findById(activityId);
    }


    @Test
    void testGetContentTypeFromFilename() throws Exception {
        var getContentTypeMethod = ActivityPhotoByActivityService.class.getDeclaredMethod("getContentTypeFromFilename", String.class);
        getContentTypeMethod.setAccessible(true);

        assertEquals("image/jpeg", getContentTypeMethod.invoke(activityPhotoByActivityService, "photo.jpg"));
        assertEquals("image/png", getContentTypeMethod.invoke(activityPhotoByActivityService, "image.png"));
        assertEquals("application/octet-stream", getContentTypeMethod.invoke(activityPhotoByActivityService, "file.unknown"));
    }


    // Helper method to create a mock ZIP file containing a specific file
    private byte[] createMockZipWithFile(String filename, byte[] content) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream)) {
            ZipEntry zipEntry = new ZipEntry(filename);
            zipOutputStream.putNextEntry(zipEntry);
            zipOutputStream.write(content);
            zipOutputStream.closeEntry();
        }
        return byteArrayOutputStream.toByteArray();
    }


}
