package org.vms.volunteer.dto;

import java.time.LocalDate;
import java.util.Objects;

public class Assignment {
    private int id;
    private String title;
    private String additionalInfo;
    private LocalDate date; //TODO MYSQL Format: YYYY-MM-DD
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
