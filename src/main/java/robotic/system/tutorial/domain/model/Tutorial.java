package robotic.system.tutorial.domain.model;

import org.hibernate.annotations.GenericGenerator;
import robotic.system.inventory.domain.model.Component;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Tutorial {

    @Id
    @GeneratedValue(generator = "uuid4")
    @GenericGenerator(name = "uuid4", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "UUID")
    private UUID id;

    private String tutorialId;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "TutorialComponent",
        joinColumns = @JoinColumn(name = "tutorial_id"),
        inverseJoinColumns = @JoinColumn(name = "component_id")
    )
    private Set<Component> components;

    public Tutorial() {
        this.tutorialId = UUID.randomUUID().toString();
    }

    Tutorial(TutorialBuilder builder) {
        this.id = UUID.randomUUID();
        this.tutorialId = builder.tutorialId;
        this.title = builder.title;
        this.content = builder.content;
        this.components = builder.components;
    }
}

