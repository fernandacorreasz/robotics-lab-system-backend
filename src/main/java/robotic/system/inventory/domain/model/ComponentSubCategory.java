package robotic.system.inventory.domain.model;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.sql.Blob;
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

    @Lob
    private Blob subCategoryImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryId", referencedColumnName = "id")
    private ComponentCategory category;

    public ComponentSubCategory() {
    }

    ComponentSubCategory(ComponentSubCategoryBuilder builder) {
        this.id = UUID.randomUUID();
        this.subCategoryId = UUID.randomUUID().toString();
        this.totalQuantity = builder.totalQuantity;
        this.subCategoryName = builder.subCategoryName;
        this.subCategoryImage = builder.subCategoryImage;
        this.category = builder.category;
    }
}
