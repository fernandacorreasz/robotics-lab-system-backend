package robotic.system.inventory.domain;

public class ComponentBuilder {
    protected String componentId;
    protected String name;
    protected String serialNumber;
    protected String description;
    protected Integer quantity;
    protected ComponentSubCategory subCategory;
    protected ComponentCategory category;

    public ComponentBuilder() {}

    public ComponentBuilder withComponentId(String componentId) {
        this.componentId = componentId;
        return this;
    }

    public ComponentBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ComponentBuilder withSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
        return this;
    }

    public ComponentBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public ComponentBuilder withQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public ComponentBuilder withSubCategory(ComponentSubCategory subCategory) {
        this.subCategory = subCategory;
        return this;
    }

    public ComponentBuilder withCategory(ComponentCategory category) {
        this.category = category;
        return this;
    }

    public Component build() {
        return new Component(this);
    }
}
