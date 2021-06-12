package timesheet;

public class Report {

    private String workerID;
    private String projectName;
    private String activityName;
    private int day;
    private int workedHours;
    private int ProjectMaxHours;
    private static int index=0;

    public Report(String workerID, String projectName, String activityName, int day, int workedHours,int ProjectMaxHours) {
        this.workerID=workerID;
        this.projectName=projectName;
        this.activityName=activityName;
        this.day=day;
        this.workedHours=workedHours;
        this.ProjectMaxHours=ProjectMaxHours;
    }

    public String getWorkerID() {
        return workerID;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getActivityName() {
        return activityName;
    }

    public int getDay() {
        return day;
    }

    public int getWorkedHours() {
        return workedHours;
    }

    public int getTimeDifference() {

        if (index == 0) {
            ++index;

            return ProjectMaxHours / 2 - workedHours;

        } else {

            return 0;

        }

    }

}
