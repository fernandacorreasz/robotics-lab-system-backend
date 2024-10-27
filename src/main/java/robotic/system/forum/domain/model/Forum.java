package robotic.system.forum.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import robotic.system.forum.domain.en.ForumStatus;
import robotic.system.user.domain.model.Users;

import java.util.Date;
import java.util.List;
import java.util.UUID;


@Getter
@Setter
@Entity
public class Forum {

    @Id
    @GeneratedValue(generator = "uuid4")
    @GenericGenerator(name = "uuid4", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "UUID")
    private UUID id;

    private String title;
    private String description;

    @Lob
    private String codeSnippet;

    @Enumerated(EnumType.STRING)
    private ForumStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date editDate;

    private int voteCount = 0;

    @OneToMany(mappedBy = "forum", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ForumComment> comments;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "forum_tag", joinColumns = @JoinColumn(name = "forum_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tag> tags;

}