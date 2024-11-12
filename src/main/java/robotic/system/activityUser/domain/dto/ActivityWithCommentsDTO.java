package robotic.system.activityUser.domain.dto;

import lombok.Getter;
import lombok.Setter;
import robotic.system.activityUser.domain.en.ActivityStatus;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ActivityWithCommentsDTO {
    private UUID id;
    private String activityTitle;
    private String activityDescription;
    private ActivityStatus activityStatus;
    private Integer timeSpent;
    private Date startDate;
    private Date endDate;
    private UUID userId;
    private String userEmail;
    private List<CommentDTO> comments;

    public ActivityWithCommentsDTO(UUID id, String activityTitle, String activityDescription, ActivityStatus activityStatus,
                                   Integer timeSpent, Date startDate, Date endDate, UUID userId, String userEmail,
                                   List<CommentDTO> comments) {
        this.id = id;
        this.activityTitle = activityTitle;
        this.activityDescription = activityDescription;
        this.activityStatus = activityStatus;
        this.timeSpent = timeSpent;
        this.startDate = startDate;
        this.endDate = endDate;
        this.userId = userId;
        this.userEmail = userEmail;
        this.comments = comments;
    }
}
