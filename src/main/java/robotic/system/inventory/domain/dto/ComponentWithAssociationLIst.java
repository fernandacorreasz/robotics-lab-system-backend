package robotic.system.inventory.domain.dto;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComponentWithAssociationLIst {
    private UUID id;
    private String componentId;
    private String name;
    private String serialNumber;
    private String description;
    private Integer quantity;
    private String tutorialLink;
    private String projectIdeas;
    private String librarySuggestions;
    private Integer defectiveQuantity; 
    private Integer discardedQuantity; 
    private String status; 
    private UUID subCategoryId;
    private String subCategoryName;
    private UUID categoryId;
    private String categoryName;

    public ComponentWithAssociationLIst(UUID id, String componentId, String name, String serialNumber,
                                        String description, Integer quantity, String tutorialLink,
                                        String projectIdeas, String librarySuggestions, Integer defectiveQuantity,
                                        Integer discardedQuantity, String status,
                                        UUID subCategoryId, String subCategoryName, UUID categoryId, String categoryName) {
        this.id = id;
        this.componentId = componentId;
        this.name = name;
        this.serialNumber = serialNumber;
        this.description = description;
        this.quantity = quantity;
        this.tutorialLink = tutorialLink;
        this.projectIdeas = projectIdeas;
        this.librarySuggestions = librarySuggestions;
        this.defectiveQuantity = defectiveQuantity;
        this.discardedQuantity = discardedQuantity;
        this.status = status;
        this.subCategoryId = subCategoryId;
        this.subCategoryName = subCategoryName;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }
}


