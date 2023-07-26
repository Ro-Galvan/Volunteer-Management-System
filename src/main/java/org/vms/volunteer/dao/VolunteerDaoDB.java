package org.vms.volunteer.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.vms.volunteer.dto.Nonprofit;
import org.vms.volunteer.dto.Skill;
import org.vms.volunteer.dto.Timesheet;
import org.vms.volunteer.dto.Volunteer;
import org.vms.volunteer.mapper.VolunteerMapper;

import java.util.List;
@Repository
public class VolunteerDaoDB implements VolunteerDao{

    @Autowired
    JdbcTemplate jdbc;

    @Override
    @Transactional
    public Volunteer addVolunteer(Volunteer volunteer) {
        //executes the SQL statement to insert new volunteer, using provided values from the Volunteer object
        final String SQL = "INSERT INTO volunteer(firstName, lastName, phoneNum, email, city, state) " + "VALUES(?,?,?,?,?,?)";
        jdbc.update(SQL,
                volunteer.getFirstName(),
                volunteer.getLastName(),
                volunteer.getPhoneNumber(),
                volunteer.getEmail(),
                volunteer.getCity(),
                volunteer.getState());

        //retrieves the auto-generated ID of the newly inserted volunteer
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        //sets the ID of the object to the newly generated ID
        volunteer.setId(newId);
        //return the modified Volunteer object
        return volunteer;
    }

    @Override
    public Volunteer getVolunteerByID(int id) {
        try {
            //executes SQL query and maps result to a Volunteer object using the VolunteerMapper class
            final String SQL = "SELECT * FROM volunteer WHERE volunteerID = ?";
            return jdbc.queryForObject(SQL, new VolunteerMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }
    //retrieves list of all Volunteer objects from the database
    @Override
    public List<Volunteer> getAllVolunteers() {
        //executes SQL query and map the results to a list of Volunteer objects using the VolunteerMapper class
        final String SQL = "SELECT * FROM volunteer";
        return jdbc.query(SQL, new VolunteerMapper());
    }

    @Override
    public void updateVolunteer(Volunteer volunteer) {
        // Executes a SQL UPDATE statement to update the volunteer in the "volunteer" table.
        final String SQL = "UPDATE volunteer SET firstName = ?, lastName = ?, phoneNum = ?, email = ?, city = ?, state = ? "
                + "WHERE PowerPK = ?";
        // Uses the Volunteer variables (from getters) from the provided volunteer object as parameters for the UPDATE statement.
        jdbc.update(SQL,
                volunteer.getFirstName(),
                volunteer.getLastName(),
                volunteer.getPhoneNumber(),
                volunteer.getEmail(),
                volunteer.getCity(),
                volunteer.getState(),
                volunteer.getId());
    }

    @Override
    @Transactional
    public void deleteVolunteerByID(int id) {
        // Delete from ALL 3 associated FK tables before deleting from Volunteer table.
//        1. DELETE FROM Nonprofit_Volunteer
        final String DELETE_NONPROFIT_VOLUNTEER = "DELETE FROM nonprofit_volunteer WHERE volunteerID = ?";
        jdbc.update(DELETE_NONPROFIT_VOLUNTEER, id);
//        2. DELETE FROM Skill
        final String DELETE_VOLUNTEER_SKILL = "DELETE FROM skill WHERE volunteerID = ?";
        jdbc.update(DELETE_VOLUNTEER_SKILL, id);
//       3. DELETE FROM Timesheet
        final String DELETE_VOLUNTEER_TIMESHEET = "DELETE FROM timesheet WHERE volunteerID = ?";
        jdbc.update(DELETE_VOLUNTEER_TIMESHEET, id);

        final String SQL = "DELETE FROM volunteer WHERE volunteerID = ?";
        jdbc.update(SQL, id);
    }

    @Override
    public List<Volunteer> getVolunteersBySkill(Skill skill) {
        final String SQL = "SELECT v.volunteerID, v.firstName, v.lastName, v.phoneNum, v.email, v.city, v.state, s.skillID, s.title AS skillTitle, s.additionalInfo " +
                "FROM Volunteer v " +
                "LEFT JOIN Skill s ON v.volunteerID = s.volunteerID " +
                "WHERE v.volunteerID = 3;";
        return jdbc.query(SQL, new VolunteerMapper(), skill.getId());
    }

//    TODO do I need the below???
//    @Override
//    public List<Volunteer> getVolunteersByNonprofit(Nonprofit nonprofit) {
//        return null;
//    }
//
//    @Override
//    public List<Volunteer> getVolunteersByTimesheet(Timesheet timesheet) {
//        return null;
//    }
}
