package robotic.system.forum.domain.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import robotic.system.user.domain.model.Users;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
public class EditHistory {

    @Id
    @GeneratedValue(generator = "uuid4")
    @GenericGenerator(name = "uuid4", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "UUID")
    private UUID id;

    @Lob
    private String previousContent;

    private String type;

    private UUID referenceId; 

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "editor_user_id")
    private Users editorUser;

    @Temporal(TemporalType.TIMESTAMP)
    private Date editDate;
}