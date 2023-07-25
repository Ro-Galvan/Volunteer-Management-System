package org.vms.volunteer.dto;

import java.util.List;
import java.util.Objects;

public class Volunteer {
    private int id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String city;
    private String state;

//    FK associations- one-to-many relationships- use composition to place an object inside another object
    private Skill skill;
    private Timesheet timesheet;

    public Volunteer() {
    }

    public Volunteer(int id, String firstName, String lastName, String phoneNumber, String email, String city, String state) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.city = city;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public Timesheet getTimesheet() {
        return timesheet;
    }

    public void setTimesheet(Timesheet timesheet) {
        this.timesheet = timesheet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Volunteer volunteer = (Volunteer) o;

        if (id != volunteer.id) return false;
        if (!Objects.equals(firstName, volunteer.firstName)) return false;
        if (!Objects.equals(lastName, volunteer.lastName)) return false;
        if (!Objects.equals(phoneNumber, volunteer.phoneNumber))
            return false;
        if (!Objects.equals(email, volunteer.email)) return false;
        if (!Objects.equals(city, volunteer.city)) return false;
        if (!Objects.equals(state, volunteer.state)) return false;
        if (!Objects.equals(skill, volunteer.skill)) return false;
        return Objects.equals(timesheet, volunteer.timesheet);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (skill != null ? skill.hashCode() : 0);
        result = 31 * result + (timesheet != null ? timesheet.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Volunteer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", skill=" + skill +
                ", timesheet=" + timesheet +
                '}';
    }
}
