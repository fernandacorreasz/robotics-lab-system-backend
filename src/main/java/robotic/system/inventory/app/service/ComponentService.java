package robotic.system.inventory.app.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import robotic.system.inventory.domain.Component;
import robotic.system.inventory.repository.ComponentRepository;

import java.util.UUID;

@Service
public class ComponentService {

    @Autowired
    private ComponentRepository componentRepository;

    public Component createComponent(Component component) {
        return componentRepository.save(component);
    }

    public Component getComponentById(UUID id) {
        return componentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Component not found"));
    }

    public Component updateComponent(UUID id, Component component) {
        if (!componentRepository.existsById(id)) {
            throw new RuntimeException("Component not found");
        }
        component.setId(id); 
        return componentRepository.save(component);
    }

    public void deleteComponent(UUID id) {
        componentRepository.deleteById(id);
    }

    public Page<Component> listComponents(Pageable pageable) {
        return componentRepository.findAll(pageable);
    }
}
