package org.vms.volunteer.dao;



import org.vms.volunteer.dto.Volunteer;

import java.util.List;

public interface VolunteerDao {
    Volunteer addVolunteer(Volunteer volunteer);
    Volunteer getVolunteerByID(int id);
    List<Volunteer> getAllVolunteers();
    void updateVolunteer(Volunteer volunteer);
    void deleteVolunteerByID(int id);
}
