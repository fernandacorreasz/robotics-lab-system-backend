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
import robotic.system.inventory.app.service.ComponentCategoryService;
import robotic.system.inventory.domain.ComponentCategory;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ComponentCategoryControllerTest {

    @Mock
    private ComponentCategoryService componentCategoryService;

    @InjectMocks
    private ComponentCategoryController componentCategoryController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCategory() {
        ComponentCategory category = new ComponentCategory();
        category.setCategoryName("Test Category");

        when(componentCategoryService.createCategory(category)).thenReturn(category);

        ResponseEntity<ComponentCategory> response = componentCategoryController.createCategory(category);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(category, response.getBody());
        verify(componentCategoryService).createCategory(category);
    }

    @Test
    void testGetCategoryById() {
        UUID categoryId = UUID.randomUUID();
        ComponentCategory category = new ComponentCategory();
        category.setId(categoryId);

        when(componentCategoryService.getCategoryById(categoryId)).thenReturn(category);

        ResponseEntity<ComponentCategory> response = componentCategoryController.getCategoryById(categoryId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(category, response.getBody());
        verify(componentCategoryService).getCategoryById(categoryId);
    }

    @Test
    void testUpdateCategory() {
        UUID categoryId = UUID.randomUUID();
        ComponentCategory category = new ComponentCategory();
        category.setCategoryName("Updated Category");

        when(componentCategoryService.updateCategory(categoryId, category)).thenReturn(category);

        ResponseEntity<ComponentCategory> response = componentCategoryController.updateCategory(categoryId, category);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(category, response.getBody());
        verify(componentCategoryService).updateCategory(categoryId, category);
    }

    @Test
    void testDeleteCategory() {
        UUID categoryId = UUID.randomUUID();

        doNothing().when(componentCategoryService).deleteCategory(categoryId);

        ResponseEntity<Void> response = componentCategoryController.deleteCategory(categoryId);

        assertEquals(204, response.getStatusCodeValue());
        verify(componentCategoryService).deleteCategory(categoryId);
    }

    @Test
    void testGetFindAllCategories() {
        PageRequest pageRequest = PageRequest.of(0, 100);
        List<ComponentCategory> categories = List.of(new ComponentCategory());
        Page<ComponentCategory> categoryPage = new PageImpl<>(categories);

        when(componentCategoryService.listCategories(pageRequest)).thenReturn(categoryPage);

        ResponseEntity<Page<ComponentCategory>> response = componentCategoryController.getFindAllCategories(0, 100);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(categoryPage, response.getBody());
        verify(componentCategoryService).listCategories(pageRequest);
    }


}
