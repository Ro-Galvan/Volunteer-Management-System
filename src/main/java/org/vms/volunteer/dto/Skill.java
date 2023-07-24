package org.vms.volunteer.dto;

import java.util.Objects;

public class Skill {
    private int id;
    private String title;
    private String additionalInfo;

    public Skill() {
    }

//  constructor to help with testing later
    public Skill(int id, String title, String additionalInfo) {
        this.id = id;
        this.title = title;
        this.additionalInfo = additionalInfo;
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

//    equals and hashcode to help compare 2 objects
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Skill skill = (Skill) o;

        if (id != skill.id) return false;
        if (!Objects.equals(title, skill.title)) return false;
        return Objects.equals(additionalInfo, skill.additionalInfo);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (additionalInfo != null ? additionalInfo.hashCode() : 0);
        return result;
    }

//    to string to help with testing
    @Override
    public String toString() {
        return "Skill{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", additionalInfo='" + additionalInfo + '\'' +
                '}';
    }
}
