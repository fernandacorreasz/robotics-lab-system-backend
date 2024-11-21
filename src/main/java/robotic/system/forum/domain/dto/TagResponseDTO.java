package robotic.system.forum.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class TagResponseDTO {
    private UUID tagId;

    public TagResponseDTO(UUID tagId) {
        this.tagId = tagId;
    }
    public UUID getTagId() {
        return tagId;
    }

    public void setTagId(UUID tagId) {
        this.tagId = tagId;
    }
}
