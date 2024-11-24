package robotic.system.inventory.app.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import robotic.system.inventory.domain.Component;
import robotic.system.inventory.domain.dto.ComponentDTO;
import robotic.system.inventory.domain.dto.ComponentWithAssociationsDTO;
import robotic.system.inventory.repository.ComponentRepository;
import robotic.system.inventory.repository.ComponentSubCategoryRepository;
import robotic.system.util.delete.BulkDeleteService;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ComponentServiceTest {

    @Mock
    private ComponentRepository componentRepository;

    @Mock
    private ComponentSubCategoryRepository componentSubCategoryRepository;

    @Mock
    private BulkDeleteService bulkDeleteService;

    @InjectMocks
    private ComponentService componentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateComponent() {
        Component component = new Component();
        component.setName("Test Component");

        when(componentRepository.save(component)).thenReturn(component);

        Component result = componentService.createComponent(component);

        assertNotNull(result);
        assertEquals("Test Component", result.getName());
        verify(componentRepository).save(component);
    }

    @Test
    void testGetComponentById_Success() {
        UUID id = UUID.randomUUID();
        Component component = new Component();
        component.setId(id);

        when(componentRepository.findById(id)).thenReturn(Optional.of(component));

        Component result = componentService.getComponentById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(componentRepository).findById(id);
    }

    @Test
    void testGetComponentById_NotFound() {
        UUID id = UUID.randomUUID();

        when(componentRepository.findById(id)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                componentService.getComponentById(id));

        assertEquals("Component not found", exception.getMessage());
        verify(componentRepository).findById(id);
    }

    @Test
    void testUpdateComponent_Success() {
        UUID id = UUID.randomUUID();
        Component component = new Component();
        component.setName("Updated Component");

        when(componentRepository.existsById(id)).thenReturn(true);
        when(componentRepository.save(component)).thenReturn(component);

        Component result = componentService.updateComponent(id, component);

        assertNotNull(result);
        assertEquals("Updated Component", result.getName());
        verify(componentRepository).existsById(id);
        verify(componentRepository).save(component);
    }

    @Test
    void testUpdateComponent_NotFound() {
        UUID id = UUID.randomUUID();
        Component component = new Component();

        when(componentRepository.existsById(id)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                componentService.updateComponent(id, component));

        assertEquals("Component not found", exception.getMessage());
        verify(componentRepository).existsById(id);
        verifyNoMoreInteractions(componentRepository);
    }

    @Test
    void testDeleteComponent() {
        UUID id = UUID.randomUUID();

        doNothing().when(componentRepository).deleteById(id);

        componentService.deleteComponent(id);

        verify(componentRepository).deleteById(id);
    }

    @Test
    void testListComponents() {
        Pageable pageable = mock(Pageable.class);
        Page<ComponentDTO> page = new PageImpl<>(Collections.emptyList());

        when(componentRepository.findAllProjected(pageable)).thenReturn(page);

        Page<ComponentDTO> result = componentService.listComponents(pageable);

        assertNotNull(result);
        assertEquals(0, result.getContent().size());
        verify(componentRepository).findAllProjected(pageable);
    }

    @Test
    void testGetComponentsBySubCategory() {
        UUID subCategoryId = UUID.randomUUID();
        List<Component> components = Collections.singletonList(new Component());

        when(componentRepository.findBySubCategoryId(subCategoryId)).thenReturn(components);

        List<Component> result = componentService.getComponentsBySubCategory(subCategoryId);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(componentRepository).findBySubCategoryId(subCategoryId);
    }

    @Test
    void testFilterComponents() {
        Pageable pageable = mock(Pageable.class);
        Page<Component> componentsPage = new PageImpl<>(Collections.emptyList());

        // Mock específico para a execução do Specification
        when(componentRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(componentsPage);

        Page<ComponentWithAssociationsDTO> result = componentService.filterComponents(Collections.emptyList(), pageable);

        assertNotNull(result);
        assertEquals(0, result.getContent().size());
        verify(componentRepository).findAll(any(Specification.class), eq(pageable));
    }


    @Test
    void testDeleteComponentsBySerialNumbers() {
        List<String> serialNumbers = Arrays.asList("SN1", "SN2");
        BulkDeleteService.BulkDeleteResult resultMock = new BulkDeleteService.BulkDeleteResult();

        when(bulkDeleteService.bulkDeleteByField(eq(serialNumbers), any(), any())).thenReturn(resultMock);

        BulkDeleteService.BulkDeleteResult result = componentService.deleteComponentsBySerialNumbers(serialNumbers);

        assertNotNull(result);
        verify(bulkDeleteService).bulkDeleteByField(eq(serialNumbers), any(), any());
    }
}
