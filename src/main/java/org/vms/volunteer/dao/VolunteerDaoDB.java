package org.vms.volunteer.dao;

import org.vms.volunteer.dto.Nonprofit;
import org.vms.volunteer.dto.Skill;
import org.vms.volunteer.dto.Timesheet;
import org.vms.volunteer.dto.Volunteer;

import java.util.List;

public class VolunteerDaoDB implements VolunteerDao{
    @Override
    public Volunteer addVolunteer(Volunteer volunteer) {
        return null;
    }

    @Override
    public Volunteer getVolunteerByID(int id) {
        return null;
    }

    @Override
    public List<Volunteer> getAllVolunteers() {
        return null;
    }

    @Override
    public void updateVolunteer(Volunteer volunteer) {

    }

    @Override
    public void deleteVolunteerByID(int id) {

    }

    @Override
    public List<Volunteer> getVolunteersBySkill(Skill skill) {
        return null;
    }

//    TODO do I need the below???
    @Override
    public List<Volunteer> getVolunteersByNonprofit(Nonprofit nonprofit) {
        return null;
    }

    @Override
    public List<Volunteer> getVolunteersByTimesheet(Timesheet timesheet) {
        return null;
    }
}
