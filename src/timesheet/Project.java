package timesheet;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;



public class Project {


    private String projectName;
    private int maxHours;
    private Map<String,Activity> activities = new HashMap<>();
    private Set<Report> reports = new HashSet<>();


    public Project(String projectName, int maxHours) {
        this.projectName=projectName;
        this.maxHours=maxHours;
    }

    public String getProjectName() {
        return projectName;
    }

    public int getMaxHours() {
        return maxHours;
    }

    public void setMaxHours(int maxHours) {
        this.maxHours = maxHours;
    }


    public void addActivity(String ActivityName,Activity activity) {
        activities.put(ActivityName,activity);

    }

    public Map<String,Activity> getActivities() {
        return activities;
    }


    @Override
    public String toString() {
        return projectName +": " + maxHours;
    }

    public Set<Report> getReports() {
        return reports;
    }

    public void addReports(Report report) {
        if(!reports.contains(report)) {
            reports.add(report);
        }


    }



}
