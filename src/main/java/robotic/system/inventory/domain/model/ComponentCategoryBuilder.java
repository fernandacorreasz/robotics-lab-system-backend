package robotic.system.inventory.domain.model;

public class ComponentCategoryBuilder {
    protected String categoryId;
    protected String categoryName;

    public ComponentCategoryBuilder() {}

    public ComponentCategoryBuilder withCategoryId(String categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public ComponentCategoryBuilder withCategoryName(String categoryName) {
        this.categoryName = categoryName;
        return this;
    }

    public ComponentCategory build() {
        return new ComponentCategory(this);
    }
}
