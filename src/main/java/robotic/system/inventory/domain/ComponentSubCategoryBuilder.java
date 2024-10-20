package robotic.system.inventory.domain;

import java.sql.Blob;

public class ComponentSubCategoryBuilder {
    protected String subCategoryId;
    protected Integer totalQuantity;
    protected String subCategoryName;
    protected ComponentCategory category;

    public ComponentSubCategoryBuilder() {}

    public ComponentSubCategoryBuilder withSubCategoryId(String subCategoryId) {
        this.subCategoryId = subCategoryId;
        return this;
    }

    public ComponentSubCategoryBuilder withTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
        return this;
    }

    public ComponentSubCategoryBuilder withSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
        return this;
    }

    public ComponentSubCategoryBuilder withCategory(ComponentCategory category) {
        this.category = category;
        return this;
    }

    public ComponentSubCategory build() {
        return new ComponentSubCategory(this);
    }
}
