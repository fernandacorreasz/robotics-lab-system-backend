package robotic.system.forum.app.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import robotic.system.forum.domain.model.EditHistory;
import robotic.system.forum.domain.model.Forum;
import robotic.system.forum.repository.EditHistoryRepository;
import robotic.system.forum.repository.ForumRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ForumServicesTest {

    @Mock
    private ForumRepository forumRepository;


    @Mock
    private EditHistoryRepository editHistoryRepository;


    @InjectMocks
    private EditHistoryService editHistoryService;


    @InjectMocks
    private ForumBulkDeleteService forumBulkDeleteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testCreateEditHistory_Successful() {
        EditHistory editHistory = new EditHistory();
        editHistory.setId(UUID.randomUUID());

        when(editHistoryRepository.save(editHistory)).thenReturn(editHistory);

        EditHistory result = editHistoryService.createEditHistory(editHistory);

        assertNotNull(result.getId(), "EditHistory ID should not be null");
        verify(editHistoryRepository).save(editHistory);
    }

}
