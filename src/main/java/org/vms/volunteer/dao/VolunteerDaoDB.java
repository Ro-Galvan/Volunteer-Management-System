package org.vms.volunteer.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.vms.volunteer.dto.Nonprofit;
import org.vms.volunteer.dto.Volunteer;
import org.vms.volunteer.mapper.NonprofitMapper;
import org.vms.volunteer.mapper.VolunteerMapper;

import java.util.List;
@Repository
public class VolunteerDaoDB implements VolunteerDao {
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

//        using helper method for bridge table
        insertIntoNonprofitVolunteerBridgeTable(volunteer);
        //return the modified Volunteer object
        return volunteer;
    }

    @Override
    public Volunteer getVolunteerByID(int id) {
        try {
            //executes SQL query and maps result to a Volunteer object using the VolunteerMapper class
            final String SQL = "SELECT * FROM volunteer WHERE volunteerID = ?";
            Volunteer volunteer = jdbc.queryForObject(SQL, new VolunteerMapper(), id);
            volunteer.setNonprofits(getNonprofitsForVolunteer(id));
            return volunteer;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    //retrieves list of all Volunteer objects from the database
    @Override
    public List<Volunteer> getAllVolunteers() {
        try {
            //executes SQL query and map the results to a list of Volunteer objects using the VolunteerMapper class
            final String SQL = "SELECT * FROM volunteer";
            List<Volunteer> volunteerList = jdbc.query(SQL, new VolunteerMapper());
            //      looping through volunteerList and setting the respective Nonprofit associated with that volunteer
            for (Volunteer volunteer : volunteerList) {
                volunteer.setNonprofits(getNonprofitsForVolunteer(volunteer.getId()));
            }
            return volunteerList;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    @Transactional
    public void updateVolunteer(Volunteer volunteer) {
        // Executes a SQL UPDATE statement to update the volunteer in the "volunteer" table.
        final String SQL = "UPDATE volunteer SET firstName = ?, lastName = ?, phoneNum = ?, email = ?, city = ?, state = ? "
                + "WHERE volunteerID = ?";
        // Uses the Volunteer variables (from getters) from the provided volunteer object as parameters for the UPDATE statement.
        jdbc.update(SQL,
                volunteer.getFirstName(),
                volunteer.getLastName(),
                volunteer.getPhoneNumber(),
                volunteer.getEmail(),
                volunteer.getCity(),
                volunteer.getState(),
                volunteer.getId());

        //  the old record needs to be deleted from bridge table before updating and adding back to bridge table
        final String DELETE_NONPROFIT_VOLUNTEER = "DELETE FROM nonprofit_volunteer WHERE volunteerID = ?";
        jdbc.update(DELETE_NONPROFIT_VOLUNTEER, volunteer.getId());

        insertIntoNonprofitVolunteerBridgeTable(volunteer);
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

//    TODO this will not work anymore since I removed skill id from volunteer--
//     look into moving to skill as a bonus feature
//    @Override
//    public List<Volunteer> getVolunteersBySkill(Skill skill) {
//        final String SQL = "SELECT v.volunteerID, v.firstName, v.lastName, v.phoneNum, v.email, v.city, v.state, s.skillID, s.title AS skillTitle, s.additionalInfo " +
//                "FROM Volunteer v " +
//                "LEFT JOIN Skill s ON v.volunteerID = s.volunteerID " +
//                "WHERE v.volunteerID = 3;";
//        return jdbc.query(SQL, new VolunteerMapper(), skill.getId());
//    }


    //    *****PRIVATE HELPER METHODS***
/*
SQL query selects all columns (n.*) from Nonprofit table, alias n, who are associated with a specific Volunteer (v.volunteerID = ?)
It achieves this by using two JOIN clauses to connect/match the nonprofit table with the volunteer table
via/ON the nonprofit_volunteer table.
 */
    private List<Nonprofit> getNonprofitsForVolunteer(int id) {
        final String SQL = "SELECT n.* " +
                "FROM nonprofit n " +
                "JOIN nonprofit_volunteer nv ON n.nonprofitID = nv.nonprofitID " +
                "JOIN volunteer v ON nv.volunteerID = v.volunteerID " +
                "WHERE v.volunteerID = ?";
        return jdbc.query(SQL, new NonprofitMapper(), id);
    }

    //    This is the bridge table for many-many relationship
    private void insertIntoNonprofitVolunteerBridgeTable(Volunteer volunteer) {
        final String SQL = "INSERT INTO nonprofit_volunteer (nonprofitID, volunteerID) " +
                "VALUES (?, ?);";
        for (Nonprofit nonprofit : volunteer.getNonprofits()) {
            jdbc.update(SQL,
                    nonprofit.getId(),
                    volunteer.getId());
        }
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