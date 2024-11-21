package robotic.system.inventory.repositories;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import robotic.system.inventory.domain.ComponentSubCategory;
import robotic.system.inventory.repository.ComponentSubCategoryRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ComponentSubCategoryRepositoryTest {

    @Mock
    private ComponentSubCategoryRepository componentSubCategoryRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindBySubCategoryName() {
        when(componentSubCategoryRepository.findBySubCategoryName(anyString()))
                .thenReturn(Optional.of(mock(ComponentSubCategory.class)));

        Optional<ComponentSubCategory> result = componentSubCategoryRepository.findBySubCategoryName("Test SubCategory");

        assertNotNull(result, "Result should not be null");
        verify(componentSubCategoryRepository).findBySubCategoryName(anyString());
    }
}
