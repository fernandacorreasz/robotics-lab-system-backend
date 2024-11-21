package robotic.system.inventory.app.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import robotic.system.inventory.app.service.ComponentBulkService;
import robotic.system.inventory.app.service.ComponentService;
import robotic.system.inventory.domain.Component;
import robotic.system.util.delete.BulkDeleteService;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ComponentControllerTest {

    @Mock
    private ComponentService componentService;

    @Mock
    private ComponentBulkService componentBulkService;

    @InjectMocks
    private ComponentController componentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetComponentById() {
        UUID id = UUID.randomUUID();
        Component component = new Component();
        when(componentService.getComponentById(id)).thenReturn(component);

        ResponseEntity<Component> response = componentController.getComponentById(id);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(component, response.getBody());
        verify(componentService).getComponentById(id);
    }

    @Test
    void testGetComponentsBySubCategory() {
        UUID subCategoryId = UUID.randomUUID();
        List<Component> components = List.of(new Component());
        when(componentService.getComponentsBySubCategory(subCategoryId)).thenReturn(components);

        ResponseEntity<List<Component>> response = componentController.getComponentsBySubCategory(subCategoryId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(components, response.getBody());
        verify(componentService).getComponentsBySubCategory(subCategoryId);
    }


    @Test
    void testCreateComponent() {
        Component component = new Component();
        when(componentService.createComponent(component)).thenReturn(component);

        ResponseEntity<Component> response = componentController.createComponent(component);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(component, response.getBody());
        verify(componentService).createComponent(component);
    }

    @Test
    void testUpdateComponent() {
        UUID id = UUID.randomUUID();
        Map<String, Object> updates = Map.of("name", "Updated Name");
        Component updatedComponent = new Component();
        when(componentService.updateComponent(id, updates)).thenReturn(updatedComponent);

        ResponseEntity<Component> response = componentController.updateComponent(id, updates);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(updatedComponent, response.getBody());
        verify(componentService).updateComponent(id, updates);
    }

    @Test
    void testDeleteComponent() {
        UUID id = UUID.randomUUID();

        doNothing().when(componentService).deleteComponent(id);

        ResponseEntity<Void> response = componentController.deleteComponent(id);

        assertEquals(204, response.getStatusCodeValue());
        verify(componentService).deleteComponent(id);
    }

    @Test
    void testBulkDeleteComponents() {
        List<String> serialNumbers = List.of("123", "456");
        BulkDeleteService.BulkDeleteResult result = new BulkDeleteService.BulkDeleteResult();
        when(componentService.deleteComponentsBySerialNumbers(serialNumbers)).thenReturn(result);

        ResponseEntity<?> response = componentController.deleteComponentsViaSerialNumbers(serialNumbers);

        assertEquals(200, response.getStatusCodeValue());
        verify(componentService).deleteComponentsBySerialNumbers(serialNumbers);
    }

}
