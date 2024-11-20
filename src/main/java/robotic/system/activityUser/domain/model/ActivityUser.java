package robotic.system.activityUser.domain.model;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import robotic.system.activityUser.domain.en.ActivityStatus;
import robotic.system.inventory.domain.Component;
import robotic.system.user.domain.model.Users;

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
    private ActivityStatus activityStatus;
    private Integer timeSpent;

    @Lob
    private String userCode;

    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "ActivityComponent", joinColumns = @JoinColumn(name = "activity_id"), inverseJoinColumns = @JoinColumn(name = "component_id"))
    private List<Component> componentsUsed;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "activity_photo_relation", joinColumns = @JoinColumn(name = "activity_id"), inverseJoinColumns = @JoinColumn(name = "photo_id"))
    private List<ActivityPhoto> photos;

    @OneToMany(mappedBy = "activity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "id")
    @JsonBackReference
    private Users user;

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
        this.userCode = builder.userCode;
        this.componentsUsed = builder.componentsUsed;
        this.photos = builder.photos;
    }
}
