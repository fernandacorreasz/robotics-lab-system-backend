package robotic.system.inventory.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import robotic.system.inventory.domain.ComponentTest;
import robotic.system.inventory.repository.ComponentTestRepository;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class ComponentTestRepositoryTest {

    @Mock
    private ComponentTestRepository componentTestRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById() {
        when(componentTestRepository.findById(any(UUID.class)))
                .thenReturn(Optional.of(mock(ComponentTest.class)));

        Optional<ComponentTest> result = componentTestRepository.findById(UUID.randomUUID());

        assertNotNull(result, "Result should not be null");
        verify(componentTestRepository).findById(any(UUID.class));
    }
}
