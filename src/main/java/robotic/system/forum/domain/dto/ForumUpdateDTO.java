package robotic.system.forum.domain.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ForumUpdateDTO {
    private UUID id;
    private String title;
    private String description;
    private String codeSnippet;
}
