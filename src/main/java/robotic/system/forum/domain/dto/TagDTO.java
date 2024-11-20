package robotic.system.forum.domain.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
public class TagDTO {
    private UUID id;
    private String name;

    public TagDTO(UUID id, String name) {
        this.id = id;
        this.name = name;
    }
}
