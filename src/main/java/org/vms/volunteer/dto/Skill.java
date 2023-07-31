package org.vms.volunteer.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

public class Skill {
    private int id;
    @NotBlank(message = "Skill title must not be empty.")
    @Size(max = 50, message="Skill title must be less than 50 characters.")
    private String title;
    @NotBlank(message = "Skill Additional Info must not be empty.")
    @Size(max = 255, message="Skill Additional Info must be less than 255 characters.")
    private String additionalInfo;
    //    FK associations- one-to-many relationships- use composition to place an object inside another object
    private Volunteer volunteer;

    public Skill() {
    }

//  constructor to help with testing later
    public Skill(int id, String title, String additionalInfo) {
        this.id = id;
        this.title = title;
        this.additionalInfo = additionalInfo;
    }
// **********This will help for testing
    public Skill(String title, String additionalInfo, Volunteer volunteer) {
        this.title = title;
        this.additionalInfo = additionalInfo;
        this.volunteer = volunteer;
    }

    public Skill(int id, String title, String additionalInfo, Volunteer volunteer) {
        this.id = id;
        this.title = title;
        this.additionalInfo = additionalInfo;
        this.volunteer = volunteer;
    }

    //    getters & setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public Volunteer getVolunteer() {
        return volunteer;
    }

    public void setVolunteer(Volunteer volunteer) {
        this.volunteer = volunteer;
    }

    //    equals and hashcode to help compare 2 objects
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Skill skill = (Skill) o;

        if (id != skill.id) return false;
        if (!Objects.equals(title, skill.title)) return false;
        if (!Objects.equals(additionalInfo, skill.additionalInfo))
            return false;
        return Objects.equals(volunteer, skill.volunteer);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (additionalInfo != null ? additionalInfo.hashCode() : 0);
        result = 31 * result + (volunteer != null ? volunteer.hashCode() : 0);
        return result;
    }


//    to string to help with testing
    @Override
    public String toString() {
        return "Skill{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", additionalInfo='" + additionalInfo + '\'' +
                ", volunteer=" + volunteer +
                '}';
    }
}
