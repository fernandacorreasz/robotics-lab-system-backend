package robotic.system.inventory.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import robotic.system.inventory.domain.ComponentSubCategory;
import robotic.system.inventory.repository.ComponentSubCategoryRepository;

import java.util.UUID;

@Service
public class ComponentSubCategoryService {

    @Autowired
    private ComponentSubCategoryRepository componentSubCategoryRepository;

    public ComponentSubCategory createSubCategory(ComponentSubCategory subCategory) {
        return componentSubCategoryRepository.save(subCategory);
    }

    public ComponentSubCategory getSubCategoryById(UUID id) {
        return componentSubCategoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SubCategory not found"));
    }

    public ComponentSubCategory updateSubCategory(UUID id, ComponentSubCategory subCategory) {
        if (!componentSubCategoryRepository.existsById(id)) {
            throw new RuntimeException("SubCategory not found");
        }
        subCategory.setId(id);
        return componentSubCategoryRepository.save(subCategory);
    }

    public void deleteSubCategory(UUID id) {
        componentSubCategoryRepository.deleteById(id);
    }

    public Page<ComponentSubCategory> listSubCategories(Pageable pageable) {
        return componentSubCategoryRepository.findAll(pageable);
    }
}
