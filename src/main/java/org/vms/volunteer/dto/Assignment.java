package org.vms.volunteer.dto;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Objects;

// One assignment can have multiple timesheets (one-to-many relationship)
public class Assignment {
    private int id;
    @NotBlank(message = "Assignment title must not be empty.")
    @Size(max = 100, message="Assignment title must be less than 100 characters.")
    private String title;
    @NotBlank(message = "Assignment Additional Info must not be empty.")
    @Size(max = 255, message="Assignment Additional Info must be less than 255 characters.")
    private String additionalInfo;
    @DateTimeFormat(pattern = "MM-dd-yyyy")
    @NotNull(message = "The date must not be empty")
//    @Past(message = "The date must be in the past")
    private LocalDate date; //MYSQL Format: YYYY-MM-DD

//    FK association-one-to-many relationships-use composition to place an object inside another object
    private Nonprofit nonprofit;

    public Assignment() {
    }

    public Assignment(int id, String title, String additionalInfo, LocalDate date, Nonprofit nonprofit) {
        this.id = id;
        this.title = title;
        this.additionalInfo = additionalInfo;
        this.date = date;
        this.nonprofit = nonprofit;
    }

    public Assignment(String title, String additionalInfo, LocalDate date, Nonprofit nonprofit) {
        this.title = title;
        this.additionalInfo = additionalInfo;
        this.date = date;
        this.nonprofit = nonprofit;
    }

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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Nonprofit getNonprofit() {
        return nonprofit;
    }

    public void setNonprofit(Nonprofit nonprofit) {
        this.nonprofit = nonprofit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Assignment that = (Assignment) o;

        if (id != that.id) return false;
        if (!Objects.equals(title, that.title)) return false;
        if (!Objects.equals(additionalInfo, that.additionalInfo))
            return false;
        if (!Objects.equals(date, that.date)) return false;
        return Objects.equals(nonprofit, that.nonprofit);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (additionalInfo != null ? additionalInfo.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (nonprofit != null ? nonprofit.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Assignment{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", additionalInfo='" + additionalInfo + '\'' +
                ", date=" + date +
                ", nonprofit=" + nonprofit +
                '}';
    }
}
