package robotic.system.inventory.app.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import robotic.system.inventory.app.service.ComponentCategoryService;
import robotic.system.inventory.domain.ComponentCategory;
import robotic.system.inventory.domain.ComponentSubCategory;

import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/categories")
public class ComponentCategoryController {

    @Autowired
    private ComponentCategoryService componentCategoryService;

    @PostMapping
    public ResponseEntity<ComponentCategory> createCategory(@RequestBody ComponentCategory category) {
        ComponentCategory createdCategory = componentCategoryService.createCategory(category);
        return ResponseEntity.ok(createdCategory);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComponentCategory> getCategoryById(@PathVariable UUID id) {
        ComponentCategory category = componentCategoryService.getCategoryById(id);
        return ResponseEntity.ok(category);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ComponentCategory> updateCategory(@PathVariable UUID id, @RequestBody ComponentCategory category) {
        ComponentCategory updatedCategory = componentCategoryService.updateCategory(id, category);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable UUID id) {
        componentCategoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/all")
    public ResponseEntity<Page<ComponentCategory>> getFindAllCategories(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "100") int size) {

        Page<ComponentCategory> categoryPage = componentCategoryService.listCategories(PageRequest.of(page, size));
        return ResponseEntity.ok(categoryPage);
    }

}
