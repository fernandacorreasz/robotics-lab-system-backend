package robotic.system.inventory.domain;

public class ComponentTestBuilder {
    protected String testDescription;
    protected Component component;

    public ComponentTestBuilder() {}

    public ComponentTestBuilder withTestDescription(String testDescription) {
        this.testDescription = testDescription;
        return this;
    }

    public ComponentTestBuilder withComponent(Component component) {
        this.component = component;
        return this;
    }

    public ComponentTest build() {
        return new ComponentTest(this);
    }
}
