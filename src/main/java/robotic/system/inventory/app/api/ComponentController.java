package robotic.system.inventory.app.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import robotic.system.inventory.app.service.ComponentService;
import robotic.system.inventory.domain.Component;

import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/components")
public class ComponentController {

    @Autowired
    private ComponentService componentService;

    @PostMapping
    public ResponseEntity<Component> createComponent(@RequestBody Component component) {
        Component createdComponent = componentService.createComponent(component);
        return ResponseEntity.ok(createdComponent);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Component> getComponentById(@PathVariable UUID id) {
        Component component = componentService.getComponentById(id);
        return ResponseEntity.ok(component);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Component> updateComponent(@PathVariable UUID id, @RequestBody Component component) {
        Component updatedComponent = componentService.updateComponent(id, component);
        return ResponseEntity.ok(updatedComponent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComponent(@PathVariable UUID id) {
        componentService.deleteComponent(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/all")
    public ResponseEntity<Page<Component>> getFindAllComponents(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "100") int size) {

        Page<Component> componentPage = componentService.listComponents(PageRequest.of(page, size));
        return ResponseEntity.ok(componentPage);
    }
}
