package robotic.system.inventory.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import robotic.system.inventory.domain.ComponentCategory;
import robotic.system.inventory.repository.ComponentCategoryRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ComponentCategoryRepositoryTest {

    @Mock
    private ComponentCategoryRepository componentCategoryRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByCategoryName() {
        when(componentCategoryRepository.findByCategoryName(anyString()))
                .thenReturn(Optional.of(mock(ComponentCategory.class)));

        Optional<ComponentCategory> result = componentCategoryRepository.findByCategoryName("Test Category");

        assertNotNull(result, "Result should not be null");
        verify(componentCategoryRepository).findByCategoryName(anyString());
    }

    @Test
    void testFindAllCategoriesWithSubcategories() {
        when(componentCategoryRepository.findAllCategoriesWithSubcategories())
                .thenReturn(List.of(mock(ComponentCategory.class)));

        List<ComponentCategory> result = componentCategoryRepository.findAllCategoriesWithSubcategories();

        assertNotNull(result, "Result should not be null");
        verify(componentCategoryRepository).findAllCategoriesWithSubcategories();
    }
}
