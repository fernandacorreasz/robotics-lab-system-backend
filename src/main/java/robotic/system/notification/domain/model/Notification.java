package robotic.system.notification.domain.model;

import org.hibernate.annotations.GenericGenerator;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Notification {
    @Id
    @GeneratedValue(generator = "uuid4")
    @GenericGenerator(name = "uuid4", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "UUID")
    private UUID id;

    private String notificationId;
    private String message;
    private String recipientEmail;

    private boolean read = false;

    @Temporal(TemporalType.TIMESTAMP)
    private Date sentAt;

    public Notification() {
        this.notificationId = UUID.randomUUID().toString();
    }
}

