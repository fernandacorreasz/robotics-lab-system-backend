package robotic.system.forum.domain.dto;

import java.util.UUID;

public class ForumCommentResponseDTO {
    private UUID commentId;

    public ForumCommentResponseDTO(UUID commentId) {
        this.commentId = commentId;
    }

    public UUID getCommentId() {
        return commentId;
    }

    public void setCommentId(UUID commentId) {
        this.commentId = commentId;
    }
}
