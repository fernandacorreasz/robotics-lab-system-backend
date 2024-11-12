package robotic.system.forum.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ForumCreateDTO {

    private String title;
    private String description;
    private String codeSnippet;
    private String status; // Será mapeado para enum ForumStatus
    private UUID userId; // ID do usuário criador
    private List<UUID> tagIds; // IDs das tags associadas
}