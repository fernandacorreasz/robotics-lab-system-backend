package robotic.system.inventory.domain.dto;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubCategoryDTO {
    private UUID subCategoryId;
    private String subCategoryName;

    public SubCategoryDTO(UUID subCategoryId, String subCategoryName) {
        this.subCategoryId = subCategoryId;
        this.subCategoryName = subCategoryName;
    }
}
