package robotic.system.inventory.domain;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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
    }
}
