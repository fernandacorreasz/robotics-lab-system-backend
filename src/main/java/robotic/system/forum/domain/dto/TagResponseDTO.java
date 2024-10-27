package robotic.system.forum.domain.dto;


import java.util.UUID;

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
