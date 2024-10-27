package robotic.system.activityUser.domain.model;

import java.util.Date;
import java.util.List;

import robotic.system.activityUser.domain.en.ActivityStatus;
import robotic.system.inventory.domain.Component;

public class ActivityUserBuilder {
    protected String activityTitle;
    protected String activityDescription;
    protected ActivityStatus activityStatus;
    protected Integer timeSpent;
    protected Date startDate;
    protected Date endDate;
    protected String userCode; 
    protected List<Component> componentsUsed;
    protected List<ActivityPhoto> photos;

    public ActivityUserBuilder() {}

    public ActivityUserBuilder withActivityTitle(String activityTitle) {
        this.activityTitle = activityTitle;
        return this;
    }

    public ActivityUserBuilder withActivityDescription(String activityDescription) {
        this.activityDescription = activityDescription;
        return this;
    }

    public ActivityUserBuilder withActivityStatus(ActivityStatus activityStatus) {
        this.activityStatus = activityStatus;
        return this;
    }

    public ActivityUserBuilder withTimeSpent(Integer timeSpent) {
        this.timeSpent = timeSpent;
        return this;
    }

    public ActivityUserBuilder withStartDate(Date startDate) {
        this.startDate = startDate;
        return this;
    }

    public ActivityUserBuilder withEndDate(Date endDate) {
        this.endDate = endDate;
        return this;
    }

    public ActivityUserBuilder withComponentsUsed(List<Component> componentsUsed) {
        this.componentsUsed = componentsUsed;
        return this;
    }

    public ActivityUserBuilder withPhotos(List<ActivityPhoto> photos) {
        this.photos = photos;
        return this;
    }

    public ActivityUser build() {
        return new ActivityUser(this);
    }

    public ActivityUserBuilder withUserCode(String userCode) { 
        this.userCode = userCode;
        return this;
    }
}
