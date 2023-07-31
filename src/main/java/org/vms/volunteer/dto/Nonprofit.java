package org.vms.volunteer.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

public class Nonprofit {
    private int id;
    @NotBlank(message = "Nonprofit name must not be empty.")
    @Size(max = 250, message="Nonprofit name must be less than 250 characters.")
    private String companyName;
    @NotBlank(message = "Nonprofit phone number must not be empty.")
    @Size(min = 12, max = 12, message="Nonprofit phone number must be exactly 10 digits and 2 hyphens (no country code allowed) example: 123-456-7890")
    private String phoneNumber;
    @NotBlank(message = "Nonprofit email must not be empty.")
    @Email(message = "Invalid email, must include an @ and be no more than 40 characters. Try again.")
    private String email;
    @NotBlank(message = "Nonprofit address must not be empty.")
    @Size(max = 255, message="Nonprofit address must be less than 255 characters.")
    private String address;
    @NotBlank(message = "Nonprofit mission must not be empty.")
    @Size(max = 255, message="Nonprofit mission must be less than 255 characters.")
    private String mission;

//  constructors
    public Nonprofit() {
    }

    public Nonprofit(int id, String companyName, String phoneNumber, String email, String address, String mission) {
        this.id = id;
        this.companyName = companyName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.mission = mission;
    }

    public Nonprofit(String companyName, String phoneNumber, String email, String address, String mission) {
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
        return Objects.equals(mission, nonprofit.mission);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (companyName != null ? companyName.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (mission != null ? mission.hashCode() : 0);
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
                '}';
    }
}
