package robotic.system.activityUser.app.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import robotic.system.activityUser.domain.model.ActivityUser;
import robotic.system.activityUser.repository.ActivityUserRepository;

import java.io.IOException;
import java.util.ArrayList;

import java.util.Optional;
import java.util.UUID;

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

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(), "Status code should be NOT_FOUND");
        assertEquals("Atividade não encontrada.", response.getBody(), "Error message should match");
        verify(activityUserRepository).findById(activityId);
    }

    @Test
    void testGetPhotoByActivityId_NoPhotosFound() throws IOException {
        UUID activityId = UUID.randomUUID();
        ActivityUser activityUser = new ActivityUser();
        activityUser.setPhotos(new ArrayList<>());

        when(activityUserRepository.findById(activityId)).thenReturn(Optional.of(activityUser));

        ResponseEntity<?> response = activityPhotoByActivityService.getPhotoByActivityId(activityId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(), "Status code should be NOT_FOUND");
        assertEquals("Nenhuma foto associada a essa atividade.", response.getBody(), "Error message should match");
        verify(activityUserRepository).findById(activityId);
    }

}
