package robotic.system.activityUser.domain.dto;

import java.util.Date;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
import robotic.system.activityUser.domain.en.ActivityStatus;

@Getter
@Setter
public class ActivityUserDTO {
    private UUID id;
    private String activityTitle;
    private String activityDescription;
    private ActivityStatus activityStatus;
    private Integer timeSpent;
    private Date startDate;
    private Date endDate;
    private UUID userId;
    private String userEmail;

    public ActivityUserDTO(UUID id, String activityTitle, String activityDescription, ActivityStatus activityStatus,
                           Integer timeSpent, Date startDate, Date endDate, UUID userId, String userEmail) {
        this.id = id;
        this.activityTitle = activityTitle;
        this.activityDescription = activityDescription;
        this.activityStatus = activityStatus;
        this.timeSpent = timeSpent;
        this.startDate = startDate;
        this.endDate = endDate;
        this.userId = userId;
        this.userEmail = userEmail;
    }
}
