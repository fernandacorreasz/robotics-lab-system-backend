package robotic.system.inventory.app.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import robotic.system.inventory.app.service.ComponentSubCategoryService;
import robotic.system.inventory.domain.ComponentSubCategory;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ComponentSubCategoryControllerTest {

    @Mock
    private ComponentSubCategoryService componentSubCategoryService;

    @InjectMocks
    private ComponentSubCategoryController componentSubCategoryController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateSubCategory() {
        ComponentSubCategory subCategory = new ComponentSubCategory();
        when(componentSubCategoryService.createSubCategory(subCategory)).thenReturn(subCategory);

        ResponseEntity<ComponentSubCategory> response = componentSubCategoryController.createSubCategory(subCategory);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(subCategory, response.getBody());
        verify(componentSubCategoryService).createSubCategory(subCategory);
    }

    @Test
    void testGetSubCategoryById() {
        UUID id = UUID.randomUUID();
        ComponentSubCategory subCategory = new ComponentSubCategory();
        when(componentSubCategoryService.getSubCategoryById(id)).thenReturn(subCategory);

        ResponseEntity<ComponentSubCategory> response = componentSubCategoryController.getSubCategoryById(id);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(subCategory, response.getBody());
        verify(componentSubCategoryService).getSubCategoryById(id);
    }

    @Test
    void testUpdateSubCategory() {
        UUID id = UUID.randomUUID();
        ComponentSubCategory subCategory = new ComponentSubCategory();
        when(componentSubCategoryService.updateSubCategory(id, subCategory)).thenReturn(subCategory);

        ResponseEntity<ComponentSubCategory> response = componentSubCategoryController.updateSubCategory(id, subCategory);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(subCategory, response.getBody());
        verify(componentSubCategoryService).updateSubCategory(id, subCategory);
    }

    @Test
    void testDeleteSubCategory() {
        UUID id = UUID.randomUUID();

        doNothing().when(componentSubCategoryService).deleteSubCategory(id);

        ResponseEntity<Void> response = componentSubCategoryController.deleteSubCategory(id);

        assertEquals(204, response.getStatusCodeValue());
        verify(componentSubCategoryService).deleteSubCategory(id);
    }

    @Test
    void testGetFindAllSubCategories() {
        PageRequest pageRequest = PageRequest.of(0, 100);
        Page<ComponentSubCategory> subCategoryPage = new PageImpl<>(List.of(new ComponentSubCategory()));
        when(componentSubCategoryService.listSubCategories(pageRequest)).thenReturn(subCategoryPage);

        ResponseEntity<Page<ComponentSubCategory>> response = componentSubCategoryController.getFindAllSubCategories(0, 100);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(subCategoryPage, response.getBody());
        verify(componentSubCategoryService).listSubCategories(pageRequest);
    }
}
