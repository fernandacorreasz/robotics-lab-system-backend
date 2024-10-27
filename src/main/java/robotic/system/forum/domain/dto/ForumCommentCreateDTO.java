package robotic.system.forum.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ForumCommentCreateDTO {

    private String content;
    private String codeSnippet;
    private UUID userId;
    private UUID forumId;
}
