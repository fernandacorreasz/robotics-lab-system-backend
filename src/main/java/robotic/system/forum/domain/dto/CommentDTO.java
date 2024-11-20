package robotic.system.forum.domain.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
public class CommentDTO {
    private UUID id;
    private String content;
    private String codeSnippet;
    private String userName;
    private UUID userId;

    public CommentDTO(UUID id, String content, String codeSnippet, String userName, UUID userId) {
        this.id = id;
        this.content = content;
        this.codeSnippet = codeSnippet;
        this.userName = userName;
        this.userId = userId;
    }
}
