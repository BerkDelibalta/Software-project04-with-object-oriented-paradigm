package timesheet;

public class Activity {

    static enum ActivityStatus{
        COMPLETED,NOT_COMPLETED
    }

    private String activityName;
    private ActivityStatus status;

    public Activity(String activityName) {
        this.activityName=activityName;
        this.status=Activity.ActivityStatus.NOT_COMPLETED;
    }

    public String getActivityName() {
        return activityName;
    }


    public ActivityStatus getStatus() {
        return status;
    }

    public void setStatus(ActivityStatus status) {
        this.status = status;
    }


}
