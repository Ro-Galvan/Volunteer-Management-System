package org.vms.volunteer.dto;
import java.time.LocalDate;
import java.util.Objects;


public class Timesheet {
    private int id;
//    private LocalTime time; // SQL equivalent is time Format: hh:mm:ss
    private String hoursLogged;
    private LocalDate date; //TODO MYSQL Format: YYYY-MM-DD

    //    FK associations- one-to-many relationships- use composition to place an object inside another object
    private Volunteer volunteer;
    private Assignment assignment;

    public Timesheet() {
    }

    public Timesheet(int id, String hoursLogged, LocalDate date, Volunteer volunteer, Assignment assignment) {
        this.id = id;
        this.hoursLogged = hoursLogged;
        this.date = date;
        this.volunteer = volunteer;
        this.assignment = assignment;
    }

//    public Timesheet(int id, String hoursLogged, LocalDate date) {
//        this.id = id;
//        this.hoursLogged = hoursLogged;
//        this.date = date;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHoursLogged() {
        return hoursLogged;
    }

    public void setHoursLogged(String hoursLogged) {
        this.hoursLogged = hoursLogged;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Volunteer getVolunteer() {
        return volunteer;
    }

    public void setVolunteer(Volunteer volunteer) {
        this.volunteer = volunteer;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Timesheet timesheet = (Timesheet) o;

        if (id != timesheet.id) return false;
        if (!Objects.equals(hoursLogged, timesheet.hoursLogged))
            return false;
        if (!Objects.equals(date, timesheet.date)) return false;
        if (!Objects.equals(volunteer, timesheet.volunteer)) return false;
        return Objects.equals(assignment, timesheet.assignment);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (hoursLogged != null ? hoursLogged.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (volunteer != null ? volunteer.hashCode() : 0);
        result = 31 * result + (assignment != null ? assignment.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Timesheet{" +
                "id=" + id +
                ", hoursLogged='" + hoursLogged + '\'' +
                ", date=" + date +
                ", volunteer=" + volunteer +
                ", assignment=" + assignment +
                '}';
    }
}
