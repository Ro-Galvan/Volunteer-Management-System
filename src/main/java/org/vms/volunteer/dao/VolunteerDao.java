package org.vms.volunteer.dao;



import org.vms.volunteer.dto.Nonprofit;
import org.vms.volunteer.dto.Skill;
import org.vms.volunteer.dto.Timesheet;
import org.vms.volunteer.dto.Volunteer;

import java.util.List;

public interface VolunteerDao {
    Volunteer addVolunteer(Volunteer volunteer);
    Volunteer getVolunteerByID(int id);
    List<Volunteer> getAllVolunteers();
    void updateVolunteer(Volunteer volunteer);
    void deleteVolunteerByID(int id);

//    TODO do I need this?
//    List<Volunteer> getVolunteersBySkill(Skill skill);

    //    needed for the many-to-many relationship that Volunteer is managing

//    TODO more so needed for testing at least it was only used in hero like that
//    List<Volunteer> getVolunteersByNonprofits(Nonprofit nonprofit);

//    TODO figure out what the page will look like, the below  is what search options you could have
//     not sure if I need all of them
//    like in sighting some were used for special features to search
//    List<Volunteer> getVolunteersByNonprofit(Nonprofit nonprofit);
//    List<Volunteer> getVolunteersByTimesheet(Timesheet timesheet);
}
