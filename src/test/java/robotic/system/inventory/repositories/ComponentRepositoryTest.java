package robotic.system.inventory.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import robotic.system.inventory.domain.Component;
import robotic.system.inventory.domain.dto.ComponentDTO;
import robotic.system.inventory.domain.dto.ComponentWithAssociationsDTO;
import robotic.system.inventory.repository.ComponentRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ComponentRepositoryTest {

    @Mock
    private ComponentRepository componentRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAllWithAssociations() {
        Page<ComponentWithAssociationsDTO> mockPage = new PageImpl<>(List.of(mock(ComponentWithAssociationsDTO.class)));
        when(componentRepository.findAllWithAssociations(any(PageRequest.class)))
                .thenReturn(mockPage);

        Page<ComponentWithAssociationsDTO> result = componentRepository.findAllWithAssociations(PageRequest.of(0, 10));

        assertNotNull(result, "Result should not be null");
        verify(componentRepository).findAllWithAssociations(any(PageRequest.class));
    }

    @Test
    void testFindAllProjected() {
        Page<ComponentDTO> mockPage = new PageImpl<>(List.of(mock(ComponentDTO.class)));
        when(componentRepository.findAllProjected(any(PageRequest.class)))
                .thenReturn(mockPage);

        Page<ComponentDTO> result = componentRepository.findAllProjected(PageRequest.of(0, 10));

        assertNotNull(result, "Result should not be null");
        verify(componentRepository).findAllProjected(any(PageRequest.class));
    }

    @Test
    void testFindBySerialNumber() {
        when(componentRepository.findBySerialNumber(anyString()))
                .thenReturn(Optional.of(mock(Component.class)));

        Optional<Component> result = componentRepository.findBySerialNumber("SN12345");

        assertNotNull(result, "Result should not be null");
        verify(componentRepository).findBySerialNumber(anyString());
    }

    @Test
    void testFindBySubCategoryId() {
        when(componentRepository.findBySubCategoryId(any(UUID.class)))
                .thenReturn(List.of(mock(Component.class)));

        List<Component> result = componentRepository.findBySubCategoryId(UUID.randomUUID());

        assertNotNull(result, "Result should not be null");
        verify(componentRepository).findBySubCategoryId(any(UUID.class));
    }

    @Test
    void testFindAllComponentsWithLoanStatusGrouped() {
        when(componentRepository.findAllComponentsWithLoanStatusGrouped())
                .thenReturn(List.<Object[]>of(new Object[]{}));

        List<Object[]> result = componentRepository.findAllComponentsWithLoanStatusGrouped();

        assertNotNull(result, "Result should not be null");
        verify(componentRepository).findAllComponentsWithLoanStatusGrouped();
    }
}
