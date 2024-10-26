package robotic.system.inventory.domain.dto;

import java.util.List;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryWithSubcategoriesDTO {
    private UUID categoryId;
    private String categoryName;
    private List<SubCategoryDTO> subcategories;

    public CategoryWithSubcategoriesDTO(UUID categoryId, String categoryName, List<SubCategoryDTO> subcategories) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.subcategories = subcategories;
    }

}
