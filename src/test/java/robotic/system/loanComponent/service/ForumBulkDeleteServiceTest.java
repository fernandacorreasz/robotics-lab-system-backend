package robotic.system.loanComponent.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import robotic.system.forum.app.service.ForumBulkDeleteService;
import robotic.system.util.delete.BulkDeleteService;

import java.util.List;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ForumBulkDeleteServiceTest {


    @Mock
    private BulkDeleteService bulkDeleteService;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private ForumBulkDeleteService forumBulkDeleteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void testDeleteForumsByIds() {

        List<String> forumIds = List.of(UUID.randomUUID().toString(), UUID.randomUUID().toString());
        BulkDeleteService.BulkDeleteResult result = new BulkDeleteService.BulkDeleteResult();

        when(bulkDeleteService.bulkDeleteByField(anyList(), any(), any())).thenReturn(result);


        BulkDeleteService.BulkDeleteResult actualResult = forumBulkDeleteService.deleteForumsByIds(forumIds);

        assertNotNull(actualResult);
        verify(bulkDeleteService).bulkDeleteByField(eq(forumIds), any(), any());
    }


    @Test
    void testDeleteForumsByIds_ThrowsRuntimeException() {
        List<String> forumIds = List.of(UUID.randomUUID().toString());
        doThrow(new RuntimeException("Erro inesperado")).when(bulkDeleteService).bulkDeleteByField(anyList(), any(), any());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                forumBulkDeleteService.deleteForumsByIds(forumIds));

        assertTrue(exception.getMessage().contains("Erro inesperado"));
    }

}