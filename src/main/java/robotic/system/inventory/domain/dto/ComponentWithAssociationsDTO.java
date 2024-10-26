package robotic.system.inventory.domain.dto;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComponentWithAssociationsDTO {
    private UUID id;
    private String componentId;
    private String name;
    private String serialNumber;
    private String description;
    private Integer quantity;
    private UUID subCategoryId;
    private String subCategoryName;

    public ComponentWithAssociationsDTO(UUID id, String componentId, String name, String serialNumber, String description, Integer quantity, UUID subCategoryId, String subCategoryName) {
        this.id = id;
        this.componentId = componentId;
        this.name = name;
        this.serialNumber = serialNumber;
        this.description = description;
        this.quantity = quantity;
        this.subCategoryId = subCategoryId;
        this.subCategoryName = subCategoryName;
    }

}
