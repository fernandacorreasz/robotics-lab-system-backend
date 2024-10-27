package robotic.system.forum.domain.dto;

import lombok.Getter;
import lombok.Setter;
import robotic.system.forum.domain.en.ForumStatus;
import robotic.system.forum.domain.model.ForumComment;
import robotic.system.forum.domain.model.Tag;

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

    private List<ForumComment> comments;
    private List<Tag> tags;

    public ForumDTO(UUID id, String title, String description, String codeSnippet, ForumStatus status, 
                    Date creationDate, Date editDate, int voteCount, List<ForumComment> comments, List<Tag> tags) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.codeSnippet = codeSnippet;
        this.status = status;
        this.creationDate = creationDate;
        this.editDate = editDate;
        this.voteCount = voteCount;
        this.comments = comments;
        this.tags = tags;
    }

    public ForumDTO() {
    }
}
