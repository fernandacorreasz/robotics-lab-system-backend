package robotic.system.activityUser.domain.model;

import org.hibernate.annotations.GenericGenerator;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import robotic.system.inventory.domain.model.Component;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
public class ActivityUser {
    @Id
    @GeneratedValue(generator = "uuid4")
    @GenericGenerator(name = "uuid4", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "UUID")
    private UUID id;

    private String activityTitle;
    private String activityDescription;
    private String activityStatus;
    private Integer timeSpent;

    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "ActivityComponent",
        joinColumns = @JoinColumn(name = "activity_id"),
        inverseJoinColumns = @JoinColumn(name = "component_id")
    )
    private List<Component> componentsUsed;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "ActivityPhoto",
        joinColumns = @JoinColumn(name = "activity_id"),
        inverseJoinColumns = @JoinColumn(name = "photo_id")
    )
    private List<ActivityPhoto> photos;

    public ActivityUser() {
    }

    ActivityUser(ActivityUserBuilder builder) {
        this.id = UUID.randomUUID();
        this.activityTitle = builder.activityTitle;
        this.activityDescription = builder.activityDescription;
        this.activityStatus = builder.activityStatus;
        this.timeSpent = builder.timeSpent;
        this.startDate = builder.startDate;
        this.endDate = builder.endDate;
        this.componentsUsed = builder.componentsUsed;
        this.photos = builder.photos;
    }
}
