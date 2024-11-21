package robotic.system.inventory.app.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import robotic.system.inventory.app.service.ComponentTestService;
import robotic.system.inventory.domain.ComponentTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ComponentTestControllerTest {

    @Mock
    private ComponentTestService componentTestService;

    @InjectMocks
    private ComponentTestController componentTestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTest() {
        ComponentTest test = new ComponentTest();
        when(componentTestService.createTest(test)).thenReturn(test);

        ResponseEntity<ComponentTest> response = componentTestController.createTest(test);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(test, response.getBody());
        verify(componentTestService).createTest(test);
    }

    @Test
    void testGetTestById() {
        UUID id = UUID.randomUUID();
        ComponentTest test = new ComponentTest();
        when(componentTestService.getTestById(id)).thenReturn(test);

        ResponseEntity<ComponentTest> response = componentTestController.getTestById(id);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(test, response.getBody());
        verify(componentTestService).getTestById(id);
    }

    @Test
    void testUpdateTest() {
        UUID id = UUID.randomUUID();
        ComponentTest test = new ComponentTest();
        when(componentTestService.updateTest(id, test)).thenReturn(test);

        ResponseEntity<ComponentTest> response = componentTestController.updateTest(id, test);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(test, response.getBody());
        verify(componentTestService).updateTest(id, test);
    }

    @Test
    void testDeleteTest() {
        UUID id = UUID.randomUUID();

        doNothing().when(componentTestService).deleteTest(id);

        ResponseEntity<Void> response = componentTestController.deleteTest(id);

        assertEquals(204, response.getStatusCodeValue());
        verify(componentTestService).deleteTest(id);
    }
}
