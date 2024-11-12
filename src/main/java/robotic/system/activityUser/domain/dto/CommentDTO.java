package robotic.system.activityUser.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class CommentDTO {
    private UUID id;
    private String text;
    private Date createdDate;

    public CommentDTO(UUID id, String text, Date createdDate) {
        this.id = id;
        this.text = text;
        this.createdDate = createdDate;
    }
}
