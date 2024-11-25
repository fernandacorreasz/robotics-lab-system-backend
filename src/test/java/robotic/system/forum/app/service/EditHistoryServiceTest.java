package robotic.system.forum.app.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import robotic.system.forum.domain.model.EditHistory;
import robotic.system.forum.repository.EditHistoryRepository;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EditHistoryServiceTest {

    @Mock
    private EditHistoryRepository editHistoryRepository;

    @InjectMocks
    private EditHistoryService editHistoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateEditHistory() {
        EditHistory editHistory = new EditHistory();
        when(editHistoryRepository.save(editHistory)).thenReturn(editHistory);

        EditHistory result = editHistoryService.createEditHistory(editHistory);

        assertNotNull(result);
        verify(editHistoryRepository).save(editHistory);
    }

    @Test
    void testGetAllEditHistories() {
        EditHistory editHistory = new EditHistory();
        List<EditHistory> mockList = Collections.singletonList(editHistory);

        when(editHistoryRepository.findAll()).thenReturn(mockList);

        List<EditHistory> result = editHistoryService.getAllEditHistories();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(editHistoryRepository).findAll();
    }

    @Test
    void testGetAllEditHistories_Empty() {
        when(editHistoryRepository.findAll()).thenReturn(Collections.emptyList());

        List<EditHistory> result = editHistoryService.getAllEditHistories();

        assertNotNull(result);;
    }
}
