package robotic.system.inventory.domain;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
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

    @Column(nullable = false)
    private String categoryId;

    @Column(nullable = false)
    private String categoryName;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ComponentSubCategory> subCategories; 

    public ComponentCategory() {
        this.categoryId = UUID.randomUUID().toString(); 
    }

    ComponentCategory(ComponentCategoryBuilder builder) {
        this.id = UUID.randomUUID();
        this.categoryId = UUID.randomUUID().toString();
        this.categoryName = builder.categoryName;
    }
}
