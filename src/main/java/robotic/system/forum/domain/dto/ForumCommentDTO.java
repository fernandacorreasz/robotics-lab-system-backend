package robotic.system.forum.domain.dto;


import lombok.Getter;
import lombok.Setter;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class ForumCommentDTO {
    private UUID id;
    private String content;
    private Date creationDate;
    private Date editDate;
    private int voteCount;

}
