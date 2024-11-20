package robotic.system.forum.app.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import robotic.system.forum.app.service.*;
import robotic.system.forum.domain.dto.ForumCommentCreateDTO;
import robotic.system.forum.domain.dto.ForumCommentResponseDTO;
import robotic.system.forum.domain.dto.ForumDTO;
import robotic.system.forum.domain.model.EditHistory;
import robotic.system.forum.domain.model.Forum;
import robotic.system.util.delete.BulkDeleteService;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ForumControllerTest {

    @Mock
    private TagService tagService;

    @Mock
    private ForumService forumService;

    @Mock
    private ForumCommentService forumCommentService;

    @Mock
    private EditHistoryService editHistoryService;

    @Mock
    private ForumBulkDeleteService forumBulkDeleteService;

    @InjectMocks
    private ForumController forumController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testCreateComment() {
        ForumCommentCreateDTO commentDTO = new ForumCommentCreateDTO();
        ForumCommentResponseDTO responseDTO = new ForumCommentResponseDTO(UUID.randomUUID());

        when(forumCommentService.createComment(commentDTO)).thenReturn(responseDTO);

        ResponseEntity<ForumCommentResponseDTO> response = forumController.createComment(commentDTO);

        assertNotNull(response);
        assertEquals(responseDTO, response.getBody());
        verify(forumCommentService).createComment(commentDTO);
    }

    @Test
    void testGetForumById() {
        UUID forumId = UUID.randomUUID();
        ForumDTO forumDTO = new ForumDTO();

        when(forumService.getForumById(forumId)).thenReturn(forumDTO);

        ResponseEntity<ForumDTO> response = forumController.getForumById(forumId);

        assertNotNull(response);
        assertEquals(forumDTO, response.getBody());
        verify(forumService).getForumById(forumId);
    }

    @Test
    void testDeleteForums() {
        List<String> forumIds = Arrays.asList("1", "2", "3");
        BulkDeleteService.BulkDeleteResult deleteResult = new BulkDeleteService.BulkDeleteResult();

        when(forumBulkDeleteService.deleteForumsByIds(forumIds)).thenReturn(deleteResult);

        ResponseEntity<BulkDeleteService.BulkDeleteResult> response = forumController.deleteForums(forumIds);

        assertNotNull(response);
        assertEquals(deleteResult, response.getBody());
        verify(forumBulkDeleteService).deleteForumsByIds(forumIds);
    }

    @Test
    void testCreateEditHistory() {
        UUID forumId = UUID.randomUUID();
        EditHistory editHistory = new EditHistory();
        Forum forum = new Forum();
        forum.setId(forumId);

        when(forumService.getAllForums()).thenReturn(List.of(forum));
        when(editHistoryService.createEditHistory(editHistory)).thenReturn(editHistory);

        ResponseEntity<EditHistory> response = forumController.createEditHistory(forumId, editHistory);

        assertNotNull(response);
        assertEquals(editHistory, response.getBody());
        verify(forumService).getAllForums();
        verify(editHistoryService).createEditHistory(editHistory);
    }
}
