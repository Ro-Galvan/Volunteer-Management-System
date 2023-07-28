package org.vms.volunteer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vms.volunteer.dao.VolunteerDao;
import org.vms.volunteer.dto.Skill;
import org.vms.volunteer.dto.Volunteer;

import java.util.List;

@Service
public class VolunteerServiceImpl implements VolunteerService{
    @Autowired
    VolunteerDao volunteerDao;

    @Override
    public Volunteer addVolunteer(Volunteer volunteer) {
        return volunteerDao.addVolunteer(volunteer);
    }

    @Override
    public Volunteer getVolunteerByID(int id) {
        return volunteerDao.getVolunteerByID(id);
    }

    @Override
    public List<Volunteer> getAllVolunteers() {
        return volunteerDao.getAllVolunteers();
    }

    @Override
    public void updateVolunteer(Volunteer volunteer) {
        volunteerDao.updateVolunteer(volunteer);
    }

    @Override
    public void deleteVolunteerByID(int id) {
        volunteerDao.deleteVolunteerByID(id);
    }

//    @Override
//    public List<Volunteer> getVolunteersBySkill(Skill skill) {
//        return volunteerDao.getVolunteersBySkill(skill);
//    }
}
