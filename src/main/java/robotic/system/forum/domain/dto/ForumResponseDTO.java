package robotic.system.forum.domain.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class ForumResponseDTO {
    private UUID id;
    private String title;
    private String description;
    private String codeSnippet;
    private String status;
    private Date creationDate;

    public ForumResponseDTO(UUID id, String title, String description, String codeSnippet, String status, Date creationDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.codeSnippet = codeSnippet;
        this.status = status;
        this.creationDate = creationDate;
    }
}
