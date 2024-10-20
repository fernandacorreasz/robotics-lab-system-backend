package robotic.system.inventory.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import robotic.system.inventory.domain.ComponentTest;
import robotic.system.inventory.repository.ComponentTestRepository;

import java.util.UUID;

@Service
public class ComponentTestService {

    @Autowired
    private ComponentTestRepository componentTestRepository;

    public ComponentTest createTest(ComponentTest test) {
        return componentTestRepository.save(test);
    }

    public ComponentTest getTestById(UUID id) {
        return componentTestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Test not found"));
    }

    public ComponentTest updateTest(UUID id, ComponentTest test) {
        if (!componentTestRepository.existsById(id)) {
            throw new RuntimeException("Test not found");
        }
        test.setId(id);
        return componentTestRepository.save(test);
    }

    public void deleteTest(UUID id) {
        componentTestRepository.deleteById(id);
    }
}
