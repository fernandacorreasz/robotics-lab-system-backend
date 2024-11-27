package robotic.system.inventory.domain;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import robotic.system.inventory.domain.en.ComponentStatus;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Component {
    @Id
    @GeneratedValue(generator = "uuid4")
    @GenericGenerator(name = "uuid4", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "UUID")
    private UUID id;

    private String componentId;
    private String name;
    private String serialNumber;
    private String description;
    private Integer quantity;

    @Column(name = "tutorial_link")
    private String tutorialLink; 

    @Column(name = "project_ideas", columnDefinition = "TEXT")
    private String projectIdeas; 

    @Column(name = "library_suggestions", columnDefinition = "TEXT")
    private String librarySuggestions; 

    @Column(name = "defective_quantity")
    private Integer defectiveQuantity = 0; 

    @Column(name = "discarded_quantity")
    private Integer discardedQuantity = 0;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ComponentStatus status = ComponentStatus.AVAILABLE; 

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subCategoryId", referencedColumnName = "id")
    @JsonBackReference
    private ComponentSubCategory subCategory;

    public Component() {
        this.componentId = UUID.randomUUID().toString();
    }

    Component(ComponentBuilder builder) {
        this.componentId = UUID.randomUUID().toString();
        this.name = builder.name;
        this.serialNumber = builder.serialNumber;
        this.description = builder.description;
        this.quantity = builder.quantity;
        this.tutorialLink = builder.tutorialLink;
        this.projectIdeas = builder.projectIdeas;
        this.librarySuggestions = builder.librarySuggestions;
        this.defectiveQuantity = builder.defectiveQuantity;
        this.discardedQuantity = builder.discardedQuantity;
        this.status = builder.status;
    }
}

