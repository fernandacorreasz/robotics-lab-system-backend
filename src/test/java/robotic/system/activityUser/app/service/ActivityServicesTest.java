package robotic.system.activityUser.app.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import robotic.system.activityUser.domain.model.ActivityUser;
import robotic.system.activityUser.domain.model.Comment;
import robotic.system.activityUser.repository.ActivityPhotoRepository;
import robotic.system.activityUser.repository.ActivityUserRepository;
import robotic.system.activityUser.repository.CommentRepository;
import robotic.system.user.domain.model.Users;
import robotic.system.user.repository.UserRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ActivityServicesTest {

    @Mock
    private ActivityUserRepository activityUserRepository;

    @Mock
    private ActivityPhotoRepository activityPhotoRepository;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ActivityPhotoFilterService activityPhotoFilterService;

    @InjectMocks
    private ActivityUpdateService activityUpdateService;

    @InjectMocks
    private ActivityUserService activityUserService;

    @InjectMocks
    private CommentService commentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetPhotoByFilename_PhotoNotFound() throws IOException {
        UUID photoId = UUID.randomUUID();
        when(activityPhotoRepository.findById(photoId)).thenReturn(Optional.empty());

        ResponseEntity<byte[]> response = activityPhotoFilterService.getPhotoByFilename(photoId);

        assertEquals(404, response.getStatusCodeValue(), "Status code should be 404");
        verify(activityPhotoRepository).findById(photoId);
    }


    @Test
    void testUpdateActivity_ActivityNotFound() {
        UUID activityId = UUID.randomUUID();
        when(activityUserRepository.findById(activityId)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                activityUpdateService.updateActivity(activityId, new ActivityUser(), null, false));

        assertTrue(exception.getMessage().contains("não encontrada"), "Exception message should mention 'não encontrada'");
        verify(activityUserRepository).findById(activityId);
    }

    @Test
    void testUpdateActivity_UpdateFieldsSuccessfully() throws IOException {
        UUID activityId = UUID.randomUUID();
        ActivityUser existingActivity = new ActivityUser();
        existingActivity.setPhotos(new ArrayList<>());

        ActivityUser updatedActivity = new ActivityUser();
        updatedActivity.setActivityTitle("Updated Title");

        when(activityUserRepository.findById(activityId)).thenReturn(Optional.of(existingActivity));
        when(activityUserRepository.save(existingActivity)).thenReturn(existingActivity);

        ActivityUser result = activityUpdateService.updateActivity(activityId, updatedActivity, null, false);

        assertEquals("Updated Title", result.getActivityTitle(), "Title should be updated");
        verify(activityUserRepository).save(existingActivity);
    }

    @Test
    void testCreateActivity_UserNotFound() {
        UUID userId = UUID.randomUUID();
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                activityUserService.createActivity(new ActivityUser(), null, userId));

        assertTrue(exception.getMessage().contains("não encontrado"), "Exception message should mention 'não encontrado'");
        verify(userRepository).findById(userId);
    }

    @Test
    void testCreateActivity_ActivityCreatedSuccessfully() throws IOException {
        UUID userId = UUID.randomUUID();
        Users user = new Users();
        ActivityUser activityUser = new ActivityUser();
        activityUser.setPhotos(new ArrayList<>());

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(activityUserRepository.save(activityUser)).thenReturn(activityUser);

        ActivityUser result = activityUserService.createActivity(activityUser, null, userId);

        assertEquals(user, result.getUser(), "User should be associated with the activity");
        verify(activityUserRepository).save(activityUser);
    }

    @Test
    void testAddCommentToActivity_ActivityNotFound() {
        UUID activityId = UUID.randomUUID();
        when(activityUserRepository.findById(activityId)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                commentService.addCommentToActivity(activityId, "Test Comment"));

        assertTrue(exception.getMessage().contains("não encontrada"), "Exception message should mention 'não encontrada'");
        verify(activityUserRepository).findById(activityId);
    }

    @Test
    void testAddCommentToActivity_CommentAddedSuccessfully() {
        UUID activityId = UUID.randomUUID();
        ActivityUser activityUser = new ActivityUser();
        Comment comment = new Comment("Test Comment", activityUser);

        when(activityUserRepository.findById(activityId)).thenReturn(Optional.of(activityUser));
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        String result = commentService.addCommentToActivity(activityId, "Test Comment");

        assertEquals("Comentário adicionado com sucesso", result, "Result message should match");
        verify(commentRepository).save(any(Comment.class));
    }
}
