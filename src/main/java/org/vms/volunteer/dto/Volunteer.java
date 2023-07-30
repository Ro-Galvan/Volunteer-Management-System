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

// managing the many-to-many relationship on the volunteer side
    private List<Nonprofit> nonprofits;

    public Volunteer() {
    }
// this constructor will be useful in tests
    public Volunteer(String firstName, String lastName, String phoneNumber, String email, String city, String state) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.city = city;
        this.state = state;
    }

    public Volunteer(String firstName, String lastName, String phoneNumber, String email, String city, String state, List<Nonprofit> nonprofits) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.city = city;
        this.state = state;
        this.nonprofits = nonprofits;
    }

    public Volunteer(int id, String firstName, String lastName, String phoneNumber, String email, String city, String state, List<Nonprofit> nonprofits) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.city = city;
        this.state = state;
        this.nonprofits = nonprofits;
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

    public List<Nonprofit> getNonprofits() {
        return nonprofits;
    }

    public void setNonprofits(List<Nonprofit> nonprofits) {
        this.nonprofits = nonprofits;
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
        return Objects.equals(nonprofits, volunteer.nonprofits);
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
        result = 31 * result + (nonprofits != null ? nonprofits.hashCode() : 0);
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
                ", nonprofits=" + nonprofits +
                '}';
    }
}
