package robotic.system.tutorial.domain.model;

import robotic.system.inventory.domain.model.Component;

import java.util.HashSet;
import java.util.Set;

public class TutorialBuilder {
    protected String tutorialId;
    protected String title;
    protected String content;
    protected Set<Component> components = new HashSet<>();

    public TutorialBuilder() {}

    public TutorialBuilder withTutorialId(String tutorialId) {
        this.tutorialId = tutorialId;
        return this;
    }

    public TutorialBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public TutorialBuilder withContent(String content) {
        this.content = content;
        return this;
    }

    public TutorialBuilder withComponents(Set<Component> components) {
        this.components = components;
        return this;
    }

    public Tutorial build() {
        return new Tutorial(this);
    }
}
