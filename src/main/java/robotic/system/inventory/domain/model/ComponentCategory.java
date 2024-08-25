package robotic.system.inventory.domain.model;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
public class ComponentCategory {
    @Id
    @GeneratedValue(generator = "uuid4")
    @GenericGenerator(name = "uuid4", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "UUID")
    private UUID id;

    private String categoryId;
    private String categoryName;

    public ComponentCategory() {
    }

    ComponentCategory(ComponentCategoryBuilder builder) {
        this.id = UUID.randomUUID();
        this.categoryId = UUID.randomUUID().toString();
        this.categoryName = builder.categoryName;
    }
}
