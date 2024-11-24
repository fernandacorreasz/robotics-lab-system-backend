package robotic.system.inventory.app.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import robotic.system.inventory.domain.ComponentCategory;
import robotic.system.inventory.domain.ComponentSubCategory;
import robotic.system.inventory.domain.dto.CategoryWithSubcategoriesDTO;
import robotic.system.inventory.repository.ComponentCategoryRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ComponentCategoryServiceTest {

    @Mock
    private ComponentCategoryRepository componentCategoryRepository;

    @InjectMocks
    private ComponentCategoryService componentCategoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCategory() {
        ComponentCategory category = new ComponentCategory();
        category.setCategoryName("Test Category");

        when(componentCategoryRepository.save(any(ComponentCategory.class))).thenReturn(category);

        ComponentCategory result = componentCategoryService.createCategory(category);

        assertNotNull(result);
        assertEquals("Test Category", result.getCategoryName());
        verify(componentCategoryRepository).save(any(ComponentCategory.class));
    }

    @Test
    void testGetCategoryById_Success() {
        UUID id = UUID.randomUUID();
        ComponentCategory category = new ComponentCategory();
        category.setId(id);

        when(componentCategoryRepository.findById(id)).thenReturn(Optional.of(category));

        ComponentCategory result = componentCategoryService.getCategoryById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(componentCategoryRepository).findById(id);
    }

    @Test
    void testGetCategoryById_NotFound() {
        UUID id = UUID.randomUUID();

        when(componentCategoryRepository.findById(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> componentCategoryService.getCategoryById(id));

        assertEquals("Category not found", exception.getMessage());
        verify(componentCategoryRepository).findById(id);
    }

    @Test
    void testUpdateCategory_Success() {
        UUID id = UUID.randomUUID();
        ComponentCategory category = new ComponentCategory();
        category.setCategoryName("Updated Category");

        when(componentCategoryRepository.existsById(id)).thenReturn(true);
        when(componentCategoryRepository.save(any(ComponentCategory.class))).thenReturn(category);

        ComponentCategory result = componentCategoryService.updateCategory(id, category);

        assertNotNull(result);
        assertEquals("Updated Category", result.getCategoryName());
        verify(componentCategoryRepository).existsById(id);
        verify(componentCategoryRepository).save(any(ComponentCategory.class));
    }

    @Test
    void testUpdateCategory_NotFound() {
        UUID id = UUID.randomUUID();
        ComponentCategory category = new ComponentCategory();

        when(componentCategoryRepository.existsById(id)).thenReturn(false);

        Exception exception = assertThrows(RuntimeException.class, () -> componentCategoryService.updateCategory(id, category));

        assertEquals("Category not found", exception.getMessage());
    }

    @Test
    void testDeleteCategory() {
        UUID id = UUID.randomUUID();

        doNothing().when(componentCategoryRepository).deleteById(id);

        componentCategoryService.deleteCategory(id);

        verify(componentCategoryRepository).deleteById(id);
    }

    @Test
    void testListCategories() {
        Page<ComponentCategory> mockPage = new PageImpl<>(Collections.emptyList());
        PageRequest pageable = PageRequest.of(0, 10);

        when(componentCategoryRepository.findAll(pageable)).thenReturn(mockPage);

        Page<ComponentCategory> result = componentCategoryService.listCategories(pageable);

        assertNotNull(result);
        assertTrue(result.getContent().isEmpty());
        verify(componentCategoryRepository).findAll(pageable);
    }

    @Test
    void testGetAllCategoriesWithSubcategories() {

        ComponentCategory category = new ComponentCategory();
        category.setId(UUID.randomUUID());
        category.setCategoryName("Test Category");

        ComponentSubCategory subCategory = new ComponentSubCategory();
        subCategory.setId(UUID.randomUUID());
        subCategory.setSubCategoryName("Test SubCategory");

        category.setSubCategories(Collections.singletonList(subCategory));

        when(componentCategoryRepository.findAllCategoriesWithSubcategories())
                .thenReturn(Collections.singletonList(category));

        List<CategoryWithSubcategoriesDTO> result = componentCategoryService.getAllCategoriesWithSubcategories();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Category", result.get(0).getCategoryName());
        assertNotNull(result.get(0).getSubcategories());
        assertEquals(1, result.get(0).getSubcategories().size());
        assertEquals("Test SubCategory", result.get(0).getSubcategories().get(0).getSubCategoryName());

        verify(componentCategoryRepository).findAllCategoriesWithSubcategories();
    }


}
