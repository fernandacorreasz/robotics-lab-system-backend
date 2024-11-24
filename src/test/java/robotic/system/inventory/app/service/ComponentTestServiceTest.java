package robotic.system.inventory.app.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import robotic.system.inventory.domain.ComponentTest;
import robotic.system.inventory.repository.ComponentTestRepository;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ComponentTestServiceTest {

    @Mock
    private ComponentTestRepository componentTestRepository;

    @InjectMocks
    private ComponentTestService componentTestService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTest() {
        ComponentTest test = new ComponentTest();
        test.setId(UUID.randomUUID());

        when(componentTestRepository.save(test)).thenReturn(test);

        ComponentTest result = componentTestService.createTest(test);

        assertNotNull(result);
        assertEquals(test.getId(), result.getId());
        verify(componentTestRepository).save(test);
    }

    @Test
    void testGetTestById_Found() {
        UUID testId = UUID.randomUUID();
        ComponentTest test = new ComponentTest();
        test.setId(testId);

        when(componentTestRepository.findById(testId)).thenReturn(Optional.of(test));

        ComponentTest result = componentTestService.getTestById(testId);

        assertNotNull(result);
        assertEquals(testId, result.getId());
        verify(componentTestRepository).findById(testId);
    }

    @Test
    void testGetTestById_NotFound() {
        UUID testId = UUID.randomUUID();

        when(componentTestRepository.findById(testId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            componentTestService.getTestById(testId);
        });

        assertEquals("Test not found", exception.getMessage());
        verify(componentTestRepository).findById(testId);
    }

    @Test
    void testUpdateTest_Success() {
        UUID testId = UUID.randomUUID();
        ComponentTest test = new ComponentTest();
        test.setId(testId);

        when(componentTestRepository.existsById(testId)).thenReturn(true);
        when(componentTestRepository.save(test)).thenReturn(test);

        ComponentTest result = componentTestService.updateTest(testId, test);

        assertNotNull(result);
        assertEquals(testId, result.getId());
        verify(componentTestRepository).existsById(testId);
        verify(componentTestRepository).save(test);
    }

    @Test
    void testUpdateTest_NotFound() {
        UUID testId = UUID.randomUUID();
        ComponentTest test = new ComponentTest();
        test.setId(testId);

        when(componentTestRepository.existsById(testId)).thenReturn(false);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            componentTestService.updateTest(testId, test);
        });

        assertEquals("Test not found", exception.getMessage());
        verify(componentTestRepository).existsById(testId);
        verify(componentTestRepository, never()).save(any(ComponentTest.class));
    }

    @Test
    void testDeleteTest() {
        UUID testId = UUID.randomUUID();

        doNothing().when(componentTestRepository).deleteById(testId);

        componentTestService.deleteTest(testId);

        verify(componentTestRepository).deleteById(testId);
    }
}
