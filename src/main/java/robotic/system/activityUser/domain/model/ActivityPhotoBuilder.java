package robotic.system.activityUser.domain.model;

import java.sql.Blob;
import java.util.List;

public class ActivityPhotoBuilder {
    protected Blob imageFile;
    protected List<ActivityUser> activities;

    public ActivityPhotoBuilder() {}

    public ActivityPhotoBuilder withImageFile(Blob imageFile) {
        this.imageFile = imageFile;
        return this;
    }

    public ActivityPhotoBuilder withActivities(List<ActivityUser> activities) {
        this.activities = activities;
        return this;
    }

    public ActivityPhoto build() {
        return new ActivityPhoto(this);
    }
}
