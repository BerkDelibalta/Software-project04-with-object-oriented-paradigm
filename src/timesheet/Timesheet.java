package timesheet;


import java.util.*;
import java.util.stream.Collectors;


public class Timesheet {

    // R1
    private List<String> days = Arrays.asList("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat");
    private List<Integer> holidaysList = new LinkedList<>();
    private Map<Integer, Integer> daysOfTheWeek = new HashMap<>();
    private Map<String, Project> projects = new HashMap<>();
    private Map<String, String> profiles = new HashMap<>();
    private int controller = 1;
    private int profileId = 0;
    private Map<String, Worker> workers = new HashMap<>();
    private int workerId = 0;
    private int total = 0;

    public void setHolidays(int... holidays) {

        if (controller == 1) {

            for (int holiday : holidays) {

                if (holiday >= 1 && !holidaysList.contains(holiday)) {
                    holidaysList.add(holiday);
                }
            }

        }

        controller = 0;
    }

    public boolean isHoliday(int day) {
        if (holidaysList.contains(day)) {
            return true;
        }
        return false;
    }

    public void setFirstWeekDay(int weekDay) throws TimesheetException {
        if (weekDay < 0 || weekDay > 6)
            throw new TimesheetException();

        switch (weekDay) {

            case 0:
                daysOfTheWeek.put(1, 0);
                daysOfTheWeek.put(2, 1);
                daysOfTheWeek.put(3, 2);
                daysOfTheWeek.put(4, 3);
                daysOfTheWeek.put(5, 4);
                daysOfTheWeek.put(6, 5);
                daysOfTheWeek.put(7, 6);
                break;
            case 1:
                daysOfTheWeek.put(1, 1);
                daysOfTheWeek.put(2, 2);
                daysOfTheWeek.put(3, 3);
                daysOfTheWeek.put(4, 4);
                daysOfTheWeek.put(5, 5);
                daysOfTheWeek.put(6, 6);
                daysOfTheWeek.put(7, 0);
                break;
            case 2:
                daysOfTheWeek.put(1, 2);
                daysOfTheWeek.put(2, 3);
                daysOfTheWeek.put(3, 4);
                daysOfTheWeek.put(4, 5);
                daysOfTheWeek.put(5, 6);
                daysOfTheWeek.put(6, 0);
                daysOfTheWeek.put(7, 1);
                break;
            case 3:
                daysOfTheWeek.put(1, 3);
                daysOfTheWeek.put(2, 4);
                daysOfTheWeek.put(3, 5);
                daysOfTheWeek.put(4, 6);
                daysOfTheWeek.put(5, 0);
                daysOfTheWeek.put(6, 1);
                daysOfTheWeek.put(7, 2);
                break;
            case 4:
                daysOfTheWeek.put(1, 4);
                daysOfTheWeek.put(2, 5);
                daysOfTheWeek.put(3, 6);
                daysOfTheWeek.put(4, 0);
                daysOfTheWeek.put(5, 1);
                daysOfTheWeek.put(6, 2);
                daysOfTheWeek.put(7, 3);
                break;
            case 5:
                daysOfTheWeek.put(1, 5);
                daysOfTheWeek.put(2, 6);
                daysOfTheWeek.put(3, 0);
                daysOfTheWeek.put(4, 1);
                daysOfTheWeek.put(5, 2);
                daysOfTheWeek.put(6, 3);
                daysOfTheWeek.put(7, 4);
                break;

            case 6:
                daysOfTheWeek.put(1, 6);
                daysOfTheWeek.put(2, 0);
                daysOfTheWeek.put(3, 1);
                daysOfTheWeek.put(4, 2);
                daysOfTheWeek.put(5, 3);
                daysOfTheWeek.put(6, 4);
                daysOfTheWeek.put(7, 5);
                break;

            default:
                daysOfTheWeek.put(1, 1);
                daysOfTheWeek.put(2, 2);
                daysOfTheWeek.put(3, 3);
                daysOfTheWeek.put(4, 4);
                daysOfTheWeek.put(5, 5);
                daysOfTheWeek.put(6, 6);
                daysOfTheWeek.put(7, 0);
                break;

        }

    }

    public int getWeekDay(int day) throws TimesheetException {

        if (day < 1)
            throw new TimesheetException();

        if (daysOfTheWeek.get(day % 7) != null) {

            return daysOfTheWeek.get(day % 7);

        } else if (day > 6) {

            return day - 7;

        } else {

            return -1;

        }

    }

    // R2
    public void createProject(String projectName, int maxHours) throws TimesheetException {
        if (maxHours < 0)
            throw new TimesheetException();

        projects.put(projectName, new Project(projectName, maxHours));

    }

    public List<String> getProjects() {
        return projects.values().stream()
                .sorted(Comparator.comparing(Project::getMaxHours).reversed()
                        .thenComparing(Comparator.comparing(Project::getProjectName)))
                .map(Project::toString).collect(Collectors.toList());

    }

    public void createActivity(String projectName, String activityName) throws TimesheetException {

        if (!projects.containsKey(projectName))
            throw new TimesheetException();

        Project project = projects.get(projectName);

        Activity activity = new Activity(activityName);

        project.addActivity(activityName, activity);

    }

    public void closeActivity(String projectName, String activityName) throws TimesheetException {
        if (!projects.containsKey(projectName))
            throw new TimesheetException();

        Project project = projects.get(projectName);

        if (project.getActivities().get(activityName) == null)
            throw new TimesheetException();

        project.getActivities().get(activityName).setStatus(Activity.ActivityStatus.COMPLETED);

    }

    public List<String> getOpenActivities(String projectName) throws TimesheetException {
        if (!projects.containsKey(projectName))
            throw new TimesheetException();

        Project project = projects.get(projectName);

        return project.getActivities().values().stream()
                .filter(e -> e.getStatus().equals(Activity.ActivityStatus.NOT_COMPLETED)).map(Activity::getActivityName)
                .sorted().collect(Collectors.toList());
    }

    // R3
    public String createProfile(int... workHours) throws TimesheetException {

        String profile = "";
        int index = 0;

        if (workHours.length != 7)
            throw new TimesheetException();

        for (String day : days) {
            int hour = workHours[index];
            if (index != 0)
                profile += "; ";
            profile += day + ": " + hour;
            ++index;
        }

        profileId++;
        profiles.put("P" + profileId, profile);

        return "P" + profileId;
    }

    public String getProfile(String profileID) throws TimesheetException {

        if (!profiles.containsKey(profileID))
            throw new TimesheetException();

        if (profiles.get(profileID) == null)
            throw new TimesheetException();

        return profiles.get(profileID);

    }

    public String createWorker(String name, String surname, String profileID) throws TimesheetException {

        if (!profiles.containsKey(profileID))
            throw new TimesheetException();

        Worker worker = new Worker(name, surname, profiles.get(profileID));

        ++workerId;

        workers.put("W" + workerId, worker);

        return "W" + workerId;

    }

    public String getWorker(String workerID) throws TimesheetException {
        if (!workers.containsKey(workerID))
            throw new TimesheetException();

        return workers.get(workerID).toString();
    }

    // R4

    public void addReport(String workerID, String projectName, String activityName, int day, int workedHours)
            throws TimesheetException {

        List<Integer> hours = new LinkedList<>();

        if (!workers.containsKey(workerID))
            throw new TimesheetException();

        if ((day <= 0 || day >= 366) || this.isHoliday(day) == true)
            throw new TimesheetException();

        if (workedHours < 0)
            throw new TimesheetException();

        try {
            for (String profile : workers.get(workerID).getProfile().split(";")) {
                hours.add(Integer.parseInt(profile.split(":")[1].trim()));
            }
        } catch (IllegalArgumentException e) {
            System.out.print(e.getMessage());
        }

        if (hours.get(day) < workedHours)
            throw new TimesheetException();

        if (!projects.containsKey(projectName))
            throw new TimesheetException();

        Project project = projects.get(projectName);

        if (!project.getActivities().containsKey(activityName))
            throw new TimesheetException();

        total += workedHours;

        if (project.getMaxHours() < total)
            throw new TimesheetException();

        if (total == 20)
            total = 0;

        if (project.getActivities().get(activityName).getStatus().equals(Activity.ActivityStatus.COMPLETED))
            throw new TimesheetException();

        project.addReports(new Report(workerID, projectName, activityName, day, workedHours, project.getMaxHours()));

    }

    public int getProjectHours(String projectName) throws TimesheetException {

        if (!projects.containsKey(projectName))
            throw new TimesheetException();

        return projects.get(projectName).getReports().stream().mapToInt(Report::getWorkedHours).sum();

    }

    public int getWorkedHoursPerDay(String workerID, int day) throws TimesheetException {

        if (!workers.containsKey(workerID))
            throw new TimesheetException();

        if (day < 1)
            throw new TimesheetException();

        return projects.values().stream().flatMap(e -> e.getReports().stream())
                .filter(e -> e.getWorkerID().equals(workerID) && e.getDay() == day).mapToInt(Report::getWorkedHours)
                .sum();
    }

    // R5
    public Map<String, Integer> countActivitiesPerWorker() {
        return projects.values().stream().distinct().flatMap(e -> e.getReports().stream())
                .filter(e -> e.getWorkedHours() >= 1)
                .collect(Collectors.groupingBy(Report::getWorkerID, Collectors.mapping(Report::getActivityName,
                        Collectors.collectingAndThen(Collectors.counting(), Long::intValue))));
    }

    public Map<String, Integer> getRemainingHoursPerProject() {

        return projects.values().stream().flatMap(e -> e.getReports().stream()).collect(Collectors
                .toMap(e -> e.getProjectName(), Report::getTimeDifference, (u1, u2) -> u1, LinkedHashMap::new));

    }

    public Map<String, Map<String, Integer>> getHoursPerActivityPerProject() {
        return projects.values().stream().distinct().flatMap(e -> e.getReports().stream())
                .collect(Collectors.groupingBy(Report::getProjectName, Collectors.groupingBy(
                        Report::getActivityName,
                        Collectors.mapping(Report::getWorkedHours, Collectors.reducing(0, (a, b) -> a + b)))));
    }

}