package robotic.system.inventory.domain.model;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subCategoryId", referencedColumnName = "id")
    private ComponentSubCategory subCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryId", referencedColumnName = "id")
    private ComponentCategory category;
    
    public Component() {
    }

    Component(ComponentBuilder builder) {
        this.id = UUID.randomUUID();
        this.componentId = UUID.randomUUID().toString();
        this.name = builder.name;
        this.serialNumber = builder.serialNumber;
        this.description = builder.description;
        this.quantity = builder.quantity;
    }
}
