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
    private String tutorialLink; // Link para o tutorial no YouTube
    private String projectIdeas; // Ideias de projetos
    private String librarySuggestions; // Sugestões de bibliotecas
    private UUID subCategoryId;
    private String subCategoryName;
    private UUID categoryId;
    private String categoryName;

    public ComponentWithAssociationsDTO(UUID id, String componentId, String name, String serialNumber,
                                        String description, Integer quantity, String tutorialLink,
                                        String projectIdeas, String librarySuggestions,
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
        this.subCategoryId = subCategoryId;
        this.subCategoryName = subCategoryName;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }
}

