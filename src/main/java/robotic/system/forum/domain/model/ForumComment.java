package robotic.system.forum.domain.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;

import robotic.system.user.domain.model.Users;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
public class ForumComment {

    @Id
    @GeneratedValue(generator = "uuid4")
    @GenericGenerator(name = "uuid4", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "UUID")
    private UUID id;

    private String content;

    private String codeSnippet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "forum_id")
    private Forum forum;

    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date editDate;

    private int voteCount = 0;
}
