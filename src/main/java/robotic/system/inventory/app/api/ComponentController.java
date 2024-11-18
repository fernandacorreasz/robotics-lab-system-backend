package robotic.system.inventory.app.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import robotic.system.inventory.app.service.ComponentBulkService;
import robotic.system.inventory.app.service.ComponentService;
import robotic.system.inventory.domain.Component;
import robotic.system.inventory.domain.dto.ComponentDTO;
import robotic.system.inventory.domain.dto.ComponentResponseDTO;
import robotic.system.inventory.domain.dto.ComponentWithAssociationsDTO;
import robotic.system.inventory.exception.ComponentValidationException;
import robotic.system.util.csv.CsvBulkUploadUtil;
import robotic.system.util.delete.BulkDeleteService;
import robotic.system.util.filter.FilterRequest;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/components")
public class ComponentController {

    @Autowired
    private ComponentService componentService;

    @Autowired
    private ComponentBulkService componentBulkService;


    @GetMapping("/{id}")
    public ResponseEntity<Component> getComponentById(@PathVariable UUID id) {
        Component component = componentService.getComponentById(id);
        return ResponseEntity.ok(component);
    }

    @GetMapping("/sub-category/{subCategoryId}")
    public ResponseEntity<List<Component>> getComponentsBySubCategory(@PathVariable UUID subCategoryId) {
        List<Component> components = componentService.getComponentsBySubCategory(subCategoryId);
        return ResponseEntity.ok(components);
    }

    @GetMapping("/{id}/with-associations")
    public ResponseEntity<ComponentWithAssociationsDTO> getComponentWithAssociationsById(@PathVariable UUID id) {
        ComponentWithAssociationsDTO component = componentService.getComponentWithAssociationsById(id);
        return ResponseEntity.ok(component);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<ComponentDTO>> getFindAllComponents(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        Page<ComponentDTO> componentsPage = componentService.listComponents(PageRequest.of(page, size));
        return ResponseEntity.ok(componentsPage);
    }

    @GetMapping("/all-with-associations")
    public ResponseEntity<Page<ComponentWithAssociationsDTO>> getFindAllComponentsWithAssociations(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        Page<ComponentWithAssociationsDTO> componentsPage = componentService
                .listComponentsWithAssociations(PageRequest.of(page, size));
        return ResponseEntity.ok(componentsPage);
    }

    @PostMapping("/filter")
    public ResponseEntity<Page<ComponentWithAssociationsDTO>> filterComponents(
            @RequestBody List<FilterRequest> filters,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        Page<ComponentWithAssociationsDTO> componentsPage = componentService.filterComponents(filters, PageRequest.of(page, size));
        return ResponseEntity.ok(componentsPage);
    }

    @PostMapping("/bulk-upload")
    public ResponseEntity<?> uploadComponents(@RequestBody String csvData) {
        try {
            CsvBulkUploadUtil.BulkUploadResult<Component> result = componentBulkService
                    .uploadComponentsFromCsvString(csvData);
            return ResponseEntity.ok(result);
        } catch (CsvBulkUploadUtil.BulkUploadException e) {
            return ResponseEntity.badRequest().body(e.getErrors());
        } catch (ComponentValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }

    @PostMapping
    public ResponseEntity<Component> createComponent(@RequestBody Component component) {
        Component createdComponent = componentService.createComponent(component);
        return ResponseEntity.ok(createdComponent);
    }

    @PostMapping("/bulk-upload-json")
    public ResponseEntity<?> uploadComponentsViaJson(@RequestBody List<Component> components) {
        try {
            List<ComponentResponseDTO> createdComponents = componentService.createComponents(components);
            return ResponseEntity.ok(createdComponents);
        } catch (ComponentValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Component> updateComponent(@PathVariable UUID id, @RequestBody Map<String, Object> updates) {
        Component updatedComponent = componentService.updateComponent(id, updates);
        return ResponseEntity.ok(updatedComponent);
    }    

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComponent(@PathVariable UUID id) {
        componentService.deleteComponent(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/bulk-delete")
    public ResponseEntity<?> deleteComponentsViaSerialNumbers(@RequestBody List<String> serialNumbers) {
        BulkDeleteService.BulkDeleteResult result = componentService.deleteComponentsBySerialNumbers(serialNumbers);

        if (result.getFailedItems().isEmpty()) {
            // Se não houver falhas, retornamos status 200 com a lista de itens deletados
            return ResponseEntity.ok(result);
        } else {
            // Se houver falhas, retornamos status 400 com a lista de falhas
            return ResponseEntity.badRequest().body(result);
        }
    }
}
    
    
