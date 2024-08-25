package robotic.system.activityUser.domain.model;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Blob;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
public class ActivityPhoto {
    @Id
    @GeneratedValue(generator = "uuid4")
    @GenericGenerator(name = "uuid4", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "UUID")
    private UUID id;

    @Lob
    private Blob imageFile;

    @ManyToMany(mappedBy = "photos")
    private List<ActivityUser> activities;

    public ActivityPhoto() {
    }

    ActivityPhoto(ActivityPhotoBuilder builder) {
        this.id = UUID.randomUUID();
        this.imageFile = builder.imageFile;
        this.activities = builder.activities;
    }
}
