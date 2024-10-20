package robotic.system.inventory.app.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import robotic.system.inventory.app.service.ComponentTestService;
import robotic.system.inventory.domain.ComponentTest;

import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/tests")
public class ComponentTestController {

    @Autowired
    private ComponentTestService componentTestService;

    @PostMapping
    public ResponseEntity<ComponentTest> createTest(@RequestBody ComponentTest test) {
        ComponentTest createdTest = componentTestService.createTest(test);
        return ResponseEntity.ok(createdTest);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComponentTest> getTestById(@PathVariable UUID id) {
        ComponentTest test = componentTestService.getTestById(id);
        return ResponseEntity.ok(test);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ComponentTest> updateTest(@PathVariable UUID id, @RequestBody ComponentTest test) {
        ComponentTest updatedTest = componentTestService.updateTest(id, test);
        return ResponseEntity.ok(updatedTest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTest(@PathVariable UUID id) {
        componentTestService.deleteTest(id);
        return ResponseEntity.noContent().build();
    }
}
