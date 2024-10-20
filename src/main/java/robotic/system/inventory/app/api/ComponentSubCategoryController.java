package robotic.system.inventory.app.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import robotic.system.inventory.app.service.ComponentSubCategoryService;
import robotic.system.inventory.domain.ComponentSubCategory;

import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/subcategories")
public class ComponentSubCategoryController {

    @Autowired
    private ComponentSubCategoryService componentSubCategoryService;

    @PostMapping
    public ResponseEntity<ComponentSubCategory> createSubCategory(@RequestBody ComponentSubCategory subCategory) {
        ComponentSubCategory createdSubCategory = componentSubCategoryService.createSubCategory(subCategory);
        return ResponseEntity.ok(createdSubCategory);
    }
    

    @GetMapping("/{id}")
    public ResponseEntity<ComponentSubCategory> getSubCategoryById(@PathVariable UUID id) {
        ComponentSubCategory subCategory = componentSubCategoryService.getSubCategoryById(id);
        return ResponseEntity.ok(subCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ComponentSubCategory> updateSubCategory(@PathVariable UUID id, @RequestBody ComponentSubCategory subCategory) {
        ComponentSubCategory updatedSubCategory = componentSubCategoryService.updateSubCategory(id, subCategory);
        return ResponseEntity.ok(updatedSubCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubCategory(@PathVariable UUID id) {
        componentSubCategoryService.deleteSubCategory(id);
        return ResponseEntity.noContent().build();
    }

    
     @GetMapping("/all")
    public ResponseEntity<Page<ComponentSubCategory>> getFindAllSubCategories(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "100") int size) {

        Page<ComponentSubCategory> subCategoryPage = componentSubCategoryService.listSubCategories(PageRequest.of(page, size));
        return ResponseEntity.ok(subCategoryPage);
    }
}
