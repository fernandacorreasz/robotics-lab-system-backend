package robotic.system.activityUser.app.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import robotic.system.activityUser.app.service.*;
import robotic.system.activityUser.domain.dto.ActivityUserDTO;
import robotic.system.activityUser.domain.model.ActivityUser;
import robotic.system.util.delete.BulkDeleteService;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class ActivityUserControllerTest {

    @Mock
    private ActivityUserService activityUserService;

    @Mock
    private DeleteActivityService deleteActivityService;

    @Mock
    private ActivityUpdateService activityUpdateService;

    @Mock
    private CommentService commentService;

    @InjectMocks
    private ActivityUserController activityUserController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testUpdateActivity() throws IOException {
        ActivityUser updatedActivity = new ActivityUser();
        updatedActivity.setId(UUID.randomUUID());

        when(activityUpdateService.updateActivity(any(UUID.class), any(ActivityUser.class), anyList(), anyBoolean()))
                .thenReturn(updatedActivity);

        String activityJson = objectMapper.writeValueAsString(updatedActivity);
        ResponseEntity<ActivityUser> response = activityUserController.updateActivity(
                UUID.randomUUID(), Collections.emptyList(), activityJson, false);

        assertNotNull(response);
        assertEquals(updatedActivity, response.getBody());
        verify(activityUpdateService).updateActivity(any(UUID.class), any(ActivityUser.class), anyList(), anyBoolean());
    }


    @Test
    void testGetFindAllActivities() {
        Page<ActivityUserDTO> activityPage = new PageImpl<>(Collections.emptyList());
        when(activityUserService.listActivities(any(PageRequest.class))).thenReturn(activityPage);

        ResponseEntity<Page<ActivityUserDTO>> response = activityUserController.getFindAllActivities(0, 10);

        assertNotNull(response);
        assertEquals(activityPage, response.getBody());
        verify(activityUserService).listActivities(any(PageRequest.class));
    }


    @Test
    void testDeleteActivities() {
        List<String> activityIds = Arrays.asList("1", "2", "3");
        BulkDeleteService.BulkDeleteResult deleteResult = new BulkDeleteService.BulkDeleteResult();

        when(deleteActivityService.deleteActivitiesByIds(activityIds)).thenReturn(deleteResult);

        ResponseEntity<BulkDeleteService.BulkDeleteResult> response = activityUserController.deleteActivities(activityIds);

        assertNotNull(response);
        assertEquals(deleteResult, response.getBody());
        verify(deleteActivityService).deleteActivitiesByIds(activityIds);
    }

    @Test
    void testAddCommentToActivity() {
        UUID activityId = UUID.randomUUID();
        String commentText = "Test comment";

        when(commentService.addCommentToActivity(activityId, commentText)).thenReturn("Comment added");

        ResponseEntity<Map<String, String>> response = activityUserController.addCommentToActivity(activityId, commentText);

        assertNotNull(response);
        assertEquals("Comment added", response.getBody().get("message"));
        verify(commentService).addCommentToActivity(activityId, commentText);
    }

}
