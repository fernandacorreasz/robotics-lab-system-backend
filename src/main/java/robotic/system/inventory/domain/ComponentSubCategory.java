package robotic.system.inventory.domain;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
@Entity
public class ComponentSubCategory {
    @Id
    @GeneratedValue(generator = "uuid4")
    @GenericGenerator(name = "uuid4", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "UUID")
    private UUID id;

    private String subCategoryId;
    private Integer totalQuantity;
    private String subCategoryName;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryId", referencedColumnName = "id")
    private ComponentCategory category;

    public ComponentSubCategory() {
        this.subCategoryId = UUID.randomUUID().toString(); 
    }

    public ComponentSubCategory(ComponentSubCategoryBuilder builder) {
        this.subCategoryId = UUID.randomUUID().toString();
        this.totalQuantity = builder.totalQuantity;
        this.subCategoryName = builder.subCategoryName;
        this.category = builder.category;
    }
}
