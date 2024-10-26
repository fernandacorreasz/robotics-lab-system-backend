package robotic.system.inventory.app.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import robotic.system.inventory.domain.ComponentCategory;
import robotic.system.inventory.domain.dto.CategoryWithSubcategoriesDTO;
import robotic.system.inventory.domain.dto.SubCategoryDTO;
import robotic.system.inventory.repository.ComponentCategoryRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ComponentCategoryService {

    @Autowired
    private ComponentCategoryRepository componentCategoryRepository;

    public ComponentCategory createCategory(ComponentCategory category) {
        if (category.getCategoryId() == null) {
            category.setCategoryId(UUID.randomUUID().toString());
        }
        return componentCategoryRepository.save(category);
    }
    

    public ComponentCategory getCategoryById(UUID id) {
        return componentCategoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    public ComponentCategory updateCategory(UUID id, ComponentCategory category) {
        if (!componentCategoryRepository.existsById(id)) {
            throw new RuntimeException("Category not found");
        }
        category.setId(id); 
        return componentCategoryRepository.save(category);
    }

    public void deleteCategory(UUID id) {
        componentCategoryRepository.deleteById(id);
    }

    public Page<ComponentCategory> listCategories(Pageable pageable) {
        return componentCategoryRepository.findAll(pageable);
    }

    
    public List<CategoryWithSubcategoriesDTO> getAllCategoriesWithSubcategories() {
        // Chama o método que faz o JOIN FETCH para garantir o carregamento das subcategorias
        List<ComponentCategory> categories = componentCategoryRepository.findAllCategoriesWithSubcategories();
        
        // Converte a entidade para o DTO
        return categories.stream()
            .map(category -> new CategoryWithSubcategoriesDTO(
                category.getId(),
                category.getCategoryName(),
                category.getSubCategories().stream()
                    .map(subCategory -> new SubCategoryDTO(subCategory.getId(), subCategory.getSubCategoryName()))
                    .collect(Collectors.toList())
            ))
            .collect(Collectors.toList());
    }
    }
