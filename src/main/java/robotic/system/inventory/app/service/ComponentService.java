package robotic.system.inventory.app.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import robotic.system.inventory.domain.Component;
import robotic.system.inventory.domain.ComponentSubCategory;
import robotic.system.inventory.domain.dto.ComponentDTO;
import robotic.system.inventory.domain.dto.ComponentResponseDTO;
import robotic.system.inventory.domain.dto.ComponentWithAssociationsDTO;
import robotic.system.inventory.exception.ComponentValidationException;
import robotic.system.inventory.exception.ValidationMessages;
import robotic.system.inventory.repository.ComponentRepository;
import robotic.system.inventory.repository.ComponentSubCategoryRepository;
import robotic.system.util.delete.BulkDeleteService;
import robotic.system.util.filter.FilterRequest;
import robotic.system.util.filter.FilterUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ComponentService {

    @Autowired
    private ComponentRepository componentRepository;

    @Autowired
    private ComponentSubCategoryRepository componentSubCategoryRepository;

    @Autowired
    private BulkDeleteService bulkDeleteService;

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

    public Page<ComponentDTO> listComponents(Pageable pageable) {
        return componentRepository.findAllProjected(pageable);
    }

   public Page<ComponentWithAssociationsDTO> listComponentsWithAssociations(Pageable pageable) {
        return componentRepository.findAllWithAssociations(pageable);
    }


 @Transactional
    public List<ComponentResponseDTO> createComponents(List<Component> components) throws ComponentValidationException {
        List<Component> createdComponents = new ArrayList<>();

        for (Component component : components) {
            // Validação do número de série
            if (componentRepository.findBySerialNumber(component.getSerialNumber()).isPresent()) {
                throw new ComponentValidationException("serialNumber",
                        String.format(ValidationMessages.DUPLICATE_SERIAL_NUMBER, component.getSerialNumber()));
            }

            // Buscar a subcategoria pelo nome
            String subCategoryName = component.getSubCategory().getSubCategoryName();
            Optional<ComponentSubCategory> subCategoryOpt = componentSubCategoryRepository
                    .findBySubCategoryName(subCategoryName);

            if (subCategoryOpt.isEmpty()) {
                throw new ComponentValidationException("subCategoryName",
                        String.format(ValidationMessages.NON_EXISTENT_SUBCATEGORY, subCategoryName));
            }

            // Associar a subcategoria ao componente
            component.setSubCategory(subCategoryOpt.get());

            // Persistir o componente
            createdComponents.add(componentRepository.save(component));
        }

        // Converter para DTO de resposta
        return createdComponents.stream()
                .map(component -> new ComponentResponseDTO(component.getId(), component.getName()))
                .collect(Collectors.toList());
    }


  @Transactional
    public BulkDeleteService.BulkDeleteResult deleteComponentsBySerialNumbers(List<String> serialNumbers) {
        return bulkDeleteService.bulkDeleteByField(
                serialNumbers,
                this::findComponentBySerialNumber,
                this::removeAndDeleteComponent
        );
    }

    private Component findComponentBySerialNumber(String serialNumber) {
        return componentRepository.findBySerialNumber(serialNumber).orElse(null);
    }

    private Boolean removeAndDeleteComponent(Component component) {
        try {
            // Remover associação com a subcategoria, mas não deletar a subcategoria
            component.setSubCategory(null);
            componentRepository.delete(component);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
 
    public Page<ComponentWithAssociationsDTO> filterComponents(List<FilterRequest> filters, Pageable pageable) {
        Specification<Component> spec = FilterUtil.byFilters(filters);
        Page<Component> componentsPage = componentRepository.findAll(spec, pageable);

        // Converter para DTO
        List<ComponentWithAssociationsDTO> dtoList = componentsPage.getContent().stream()
            .map(component -> new ComponentWithAssociationsDTO(
                component.getId(),
                component.getComponentId(),
                component.getName(),
                component.getSerialNumber(),
                component.getDescription(),
                component.getQuantity(),
                component.getSubCategory() != null ? component.getSubCategory().getId() : null,
                component.getSubCategory() != null ? component.getSubCategory().getSubCategoryName() : null
            ))
            .collect(Collectors.toList());

        return new PageImpl<>(dtoList, pageable, componentsPage.getTotalElements());
    }

    public List<Component> getComponentsBySubCategory(UUID subCategoryId) {
        return componentRepository.findBySubCategoryId(subCategoryId);
    }

    //alterar
    public ComponentWithAssociationsDTO getComponentWithAssociationsById(UUID id) {
        Component component = componentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Component not found"));
        return new ComponentWithAssociationsDTO(
                component.getId(),
                component.getComponentId(),
                component.getName(),
                component.getSerialNumber(),
                component.getDescription(),
                component.getQuantity(),
                component.getSubCategory() != null ? component.getSubCategory().getId() : null,
                component.getSubCategory() != null ? component.getSubCategory().getSubCategoryName() : null
        );
    }

    public Component updateComponent(UUID id, Map<String, Object> updates) {
        Component component = componentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Component not found"));

        // Atualiza somente os campos informados no Map
        updates.forEach((key, value) -> {
            switch (key) {
                case "name":
                    component.setName((String) value);
                    break;
                case "serialNumber":
                    component.setSerialNumber((String) value);
                    break;
                case "description":
                    component.setDescription((String) value);
                    break;
                case "quantity":
                    component.setQuantity((Integer) value);
                    break;
                case "subCategoryId":
                    // Se a subCategoria for atualizada, buscar a subCategoria correspondente
                    if (value != null) {
                        UUID subCategoryId = UUID.fromString((String) value);
                        ComponentSubCategory subCategory = componentSubCategoryRepository.findById(subCategoryId)
                                .orElseThrow(() -> new RuntimeException("SubCategory not found"));
                        component.setSubCategory(subCategory);
                    } else {
                        component.setSubCategory(null);
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Campo " + key + " não é válido para atualização.");
            }
        });

        return componentRepository.save(component);
    }

}
