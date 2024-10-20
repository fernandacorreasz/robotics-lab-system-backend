package robotic.system.inventory.domain;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
public class ComponentTest {
    @Id
    @GeneratedValue(generator = "uuid4")
    @GenericGenerator(name = "uuid4", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "UUID")
    private UUID id;

    private String testDescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "componentId", referencedColumnName = "id")
    private Component component;

    public ComponentTest() {
    }

    ComponentTest(ComponentTestBuilder builder) {
        this.id = UUID.randomUUID();
        this.testDescription = builder.testDescription;
        this.component = builder.component;
    }
}
