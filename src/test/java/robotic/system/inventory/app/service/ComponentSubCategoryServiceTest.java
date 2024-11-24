package robotic.system.inventory.app.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import robotic.system.inventory.domain.ComponentSubCategory;
import robotic.system.inventory.repository.ComponentSubCategoryRepository;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ComponentSubCategoryServiceTest {

    @Mock
    private ComponentSubCategoryRepository componentSubCategoryRepository;

    @InjectMocks
    private ComponentSubCategoryService componentSubCategoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateSubCategory() {
        ComponentSubCategory subCategory = new ComponentSubCategory();
        subCategory.setSubCategoryName("Test SubCategory");

        when(componentSubCategoryRepository.save(subCategory)).thenReturn(subCategory);

        ComponentSubCategory result = componentSubCategoryService.createSubCategory(subCategory);

        assertNotNull(result);
        assertEquals("Test SubCategory", result.getSubCategoryName());
        verify(componentSubCategoryRepository).save(subCategory);
    }

    @Test
    void testGetSubCategoryById_Success() {
        UUID id = UUID.randomUUID();
        ComponentSubCategory subCategory = new ComponentSubCategory();
        subCategory.setId(id);

        when(componentSubCategoryRepository.findById(id)).thenReturn(Optional.of(subCategory));

        ComponentSubCategory result = componentSubCategoryService.getSubCategoryById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(componentSubCategoryRepository).findById(id);
    }

    @Test
    void testGetSubCategoryById_NotFound() {
        UUID id = UUID.randomUUID();

        when(componentSubCategoryRepository.findById(id)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                componentSubCategoryService.getSubCategoryById(id));

        assertEquals("SubCategory not found", exception.getMessage());
        verify(componentSubCategoryRepository).findById(id);
    }

    @Test
    void testUpdateSubCategory_Success() {
        UUID id = UUID.randomUUID();
        ComponentSubCategory subCategory = new ComponentSubCategory();
        subCategory.setSubCategoryName("Updated SubCategory");

        when(componentSubCategoryRepository.existsById(id)).thenReturn(true);
        when(componentSubCategoryRepository.save(subCategory)).thenReturn(subCategory);

        ComponentSubCategory result = componentSubCategoryService.updateSubCategory(id, subCategory);

        assertNotNull(result);
        assertEquals("Updated SubCategory", result.getSubCategoryName());
        verify(componentSubCategoryRepository).existsById(id);
        verify(componentSubCategoryRepository).save(subCategory);
    }

    @Test
    void testUpdateSubCategory_NotFound() {
        UUID id = UUID.randomUUID();
        ComponentSubCategory subCategory = new ComponentSubCategory();

        when(componentSubCategoryRepository.existsById(id)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                componentSubCategoryService.updateSubCategory(id, subCategory));

        assertEquals("SubCategory not found", exception.getMessage());
        verify(componentSubCategoryRepository).existsById(id);
        verifyNoMoreInteractions(componentSubCategoryRepository);
    }

    @Test
    void testDeleteSubCategory() {
        UUID id = UUID.randomUUID();

        doNothing().when(componentSubCategoryRepository).deleteById(id);

        componentSubCategoryService.deleteSubCategory(id);

        verify(componentSubCategoryRepository).deleteById(id);
    }

    @Test
    void testListSubCategories() {
        Pageable pageable = mock(Pageable.class);
        Page<ComponentSubCategory> page = new PageImpl<>(Collections.emptyList());

        when(componentSubCategoryRepository.findAll(pageable)).thenReturn(page);

        Page<ComponentSubCategory> result = componentSubCategoryService.listSubCategories(pageable);

        assertNotNull(result);
        assertEquals(0, result.getContent().size());
        verify(componentSubCategoryRepository).findAll(pageable);
    }
}
