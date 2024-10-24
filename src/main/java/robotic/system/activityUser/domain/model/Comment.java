package robotic.system.activityUser.domain.model;

import org.hibernate.annotations.GenericGenerator;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Comment {
    @Id
    @GeneratedValue(generator = "uuid4")
    @GenericGenerator(name = "uuid4", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "UUID")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_id", nullable = false)
    private ActivityUser activity;

    private String text; 

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    public Comment() {
        this.createdDate = new Date();
    }

    public Comment(String text, ActivityUser activity) {
        this.text = text;
        this.activity = activity;
        this.createdDate = new Date();
    }
}
