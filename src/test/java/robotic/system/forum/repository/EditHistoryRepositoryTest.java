package robotic.system.forum.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import robotic.system.forum.domain.model.EditHistory;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EditHistoryRepositoryTest {

    @Mock
    private EditHistoryRepository editHistoryRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById() {
        when(editHistoryRepository.findById(any(UUID.class))).thenReturn(Optional.of(mock(EditHistory.class)));

        Optional<EditHistory> result = editHistoryRepository.findById(UUID.randomUUID());

        assertNotNull(result, "Result should not be null");
        verify(editHistoryRepository).findById(any(UUID.class));
    }

    @Test
    void testSave() {
        EditHistory editHistory = mock(EditHistory.class);
        when(editHistoryRepository.save(any(EditHistory.class))).thenReturn(editHistory);

        EditHistory result = editHistoryRepository.save(editHistory);

        assertNotNull(result, "Saved EditHistory should not be null");
        verify(editHistoryRepository).save(any(EditHistory.class));
    }

    @Test
    void testDeleteById() {
        doNothing().when(editHistoryRepository).deleteById(any(UUID.class));

        editHistoryRepository.deleteById(UUID.randomUUID());

        verify(editHistoryRepository).deleteById(any(UUID.class));
    }
}
