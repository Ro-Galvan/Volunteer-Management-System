package org.vms.volunteer.service;

import org.vms.volunteer.dto.Skill;
import org.vms.volunteer.dto.Volunteer;

import java.util.List;

public interface VolunteerService {
    Volunteer addVolunteer(Volunteer volunteer);
    Volunteer getVolunteerByID(int id);
    List<Volunteer> getAllVolunteers();
    void updateVolunteer(Volunteer volunteer);
    void deleteVolunteerByID(int id);
    //    TODO- not sure how this method will be used on front end
//    List<Volunteer> getVolunteersBySkill(Skill skill);
}
