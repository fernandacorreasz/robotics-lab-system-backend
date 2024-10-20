package robotic.system.inventory.app.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import robotic.system.inventory.domain.ComponentCategory;
import robotic.system.inventory.repository.ComponentCategoryRepository;

import java.util.UUID;

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

}
