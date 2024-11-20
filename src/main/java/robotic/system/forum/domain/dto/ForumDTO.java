package robotic.system.forum.domain.dto;

import lombok.Getter;
import lombok.Setter;
import robotic.system.forum.domain.en.ForumStatus;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ForumDTO {

    private UUID id;
    private String title;
    private String description;
    private String codeSnippet;
    private ForumStatus status;
    private Date creationDate;
    private Date editDate;
    private int voteCount;
    private String userName;
    private UUID userId;
    private List<CommentDTO> comments;
    private List<TagDTO> tags;

    public ForumDTO(UUID id, String title, String description, String codeSnippet, ForumStatus status,
                    Date creationDate, Date editDate, int voteCount, String userName, UUID userId,
                    List<CommentDTO> comments, List<TagDTO> tags) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.codeSnippet = codeSnippet;
        this.status = status;
        this.creationDate = creationDate;
        this.editDate = editDate;
        this.voteCount = voteCount;
        this.userName = userName;
        this.userId = userId;
        this.comments = comments;
        this.tags = tags;
    }

    public ForumDTO() {
    }
}
