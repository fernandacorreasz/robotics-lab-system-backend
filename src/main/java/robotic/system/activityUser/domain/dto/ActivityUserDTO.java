package robotic.system.activityUser.domain.dto;

import java.util.Date;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActivityUserDTO {
    private UUID id;
    private String activityTitle;
    private String activityDescription;
    private String activityStatus;
    private Integer timeSpent;
    private Date startDate;
    private Date endDate;

    public ActivityUserDTO(UUID id, String activityTitle, String activityDescription,
                           String activityStatus, Integer timeSpent, Date startDate, Date endDate) {
        this.id = id;
        this.activityTitle = activityTitle;
        this.activityDescription = activityDescription;
        this.activityStatus = activityStatus;
        this.timeSpent = timeSpent;
        this.startDate = startDate;
        this.endDate = endDate;
    }

}
