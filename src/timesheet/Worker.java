package timesheet;

public class Worker {

    private String name;
    private String surname;
    private String profile;

    public Worker(String name, String surname, String profile) {
        this.name=name;
        this.surname=surname;
        this.profile=profile;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getProfile() {
        return profile;
    }

    public String toString() {
        return name +" "+ surname+" ("+profile+")";
    }

}
