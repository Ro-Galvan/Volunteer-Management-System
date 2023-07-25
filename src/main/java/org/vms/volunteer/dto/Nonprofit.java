package org.vms.volunteer.dto;

import java.util.List;
import java.util.Objects;

public class Nonprofit {
    private int id;
    private String companyName;
    private String phoneNumber;
    private String email;
    private String address;
    private String mission;
    /* managing the many-to-many relationship on the Nonprofit side since it is more likely that
    nonprofits will have more volunteers VS a volunteer that may only volunteer 1 time at 1 place */
    private List<Volunteer> volunteers;

//  constructors
    public Nonprofit() {
    }

//    TODO do i need to add the list later - left off for now because a list as a param is messy
    public Nonprofit(int id, String companyName, String phoneNumber, String email, String address, String mission) {
        this.id = id;
        this.companyName = companyName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.mission = mission;
    }

//    getters & setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMission() {
        return mission;
    }

    public void setMission(String mission) {
        this.mission = mission;
    }

    public List<Volunteer> getVolunteers() {
        return volunteers;
    }

    public void setVolunteers(List<Volunteer> volunteers) {
        this.volunteers = volunteers;
    }

    //    equals & hashcode to compare 2 objects
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Nonprofit nonprofit = (Nonprofit) o;

        if (id != nonprofit.id) return false;
        if (!Objects.equals(companyName, nonprofit.companyName))
            return false;
        if (!Objects.equals(phoneNumber, nonprofit.phoneNumber))
            return false;
        if (!Objects.equals(email, nonprofit.email)) return false;
        if (!Objects.equals(address, nonprofit.address)) return false;
        if (!Objects.equals(mission, nonprofit.mission)) return false;
        return Objects.equals(volunteers, nonprofit.volunteers);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (companyName != null ? companyName.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (mission != null ? mission.hashCode() : 0);
        result = 31 * result + (volunteers != null ? volunteers.hashCode() : 0);
        return result;
    }

    //    toString to help with debugging and tests

    @Override
    public String toString() {
        return "Nonprofit{" +
                "id=" + id +
                ", companyName='" + companyName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", mission='" + mission + '\'' +
                ", volunteers=" + volunteers +
                '}';
    }
}
