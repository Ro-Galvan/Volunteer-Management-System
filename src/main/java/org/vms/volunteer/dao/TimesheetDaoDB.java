package org.vms.volunteer.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.vms.volunteer.dto.Assignment;
import org.vms.volunteer.dto.Timesheet;
import org.vms.volunteer.dto.Volunteer;
import org.vms.volunteer.mapper.VolunteerMapper;

import java.util.List;
@Repository
public class TimesheetDaoDB implements TimesheetDao{
    @Autowired
    JdbcTemplate jdbc;
    @Override
    @Transactional
    public Timesheet addTimesheet(Timesheet timesheet) {
//executes the SQL statement to insert new timesheet, using provided values from the Timesheet object
        final String SQL = "INSERT INTO timesheet(hoursLogged, date, volunteerID, assignmentID) " + "VALUES(?,?,?,?)";
        jdbc.update(SQL,
                timesheet.getHoursLogged(),
                timesheet.getDate(),
                timesheet.getVolunteer().getId(),
                timesheet.getAssignment().getId());

        //retrieves the last inserted auto-generated ID of the newly inserted skill
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        //sets the ID of the object to the newly generated ID
        timesheet.setId(newId);
//        TODO private methods go here to get Volunteer + Assignment ids
        Volunteer volunteer = getVolunteerForTimesheet(timesheet.getVolunteer().getId());
        timesheet.setVolunteer(volunteer);

        Assignment assignment = getAssignmentForTimesheet(timesheet.getAssignment().getId());
        timesheet.setAssignment(assignment);
        //return the modified timesheet object
        return timesheet;
    }

    @Override
    public Timesheet getTimesheetByID(int id) {
        return null;
    }

    @Override
    public List<Timesheet> getAllTimesheets() {
        return null;
    }

    @Override
    public void updateTimesheet(Timesheet timesheet) {

    }

    @Override
    public void deleteTimesheetByID(int id) {

    }

    //    *****PRIVATE HELPER METHODS***

    private Volunteer getVolunteerForTimesheet(int volunteerId) {
        try{
            //With an INNER JOIN, only the skills that have a matching volunteerID in the volunteer table will be included in the result set.
//            TODO need to update the SQL query to use timesheet table instead of Skill
            final String SELECT_VOLUNTEER_FOR_TIMESHEET = "SELECT v.* FROM skill s JOIN volunteer v ON s.volunteerID = v.volunteerID WHERE s.skillID = ?;";
//            what I had earlier that was wrong:
//            final String SELECT_VOLUNTEER_FOR_SKILL = "SELECT v.* FROM skill s JOIN volunteer v ON s.volunteerID = v.volunteerID WHERE s.volunteerID = ?;";

//            JDBC queryForObject brings back 1 row and wrapped in a try catch but query for doesn't
            Volunteer volunteer = jdbc.queryForObject(SELECT_VOLUNTEER_FOR_TIMESHEET, new VolunteerMapper(), volunteerId);
            return volunteer;
        }
        catch (DataAccessException ex){
            return null;
        }
    }


    private Assignment getAssignmentForTimesheet(int id){
//        try {
// //   TODO need to update the SQL query & add the assignment mapper once it's DAO is complete
//            final String SQL = "SELECT p.* FROM hero h " +
//                    "JOIN power p ON h.PowerPK = p.PowerPK WHERE h.HeroPK = ?;";
//            return jdbc.queryForObject(SQL, new PowerMapper(), id);
//        } catch (DataAccessException ex) {
//            return null;
//        }

        return null;
    }

//    @Override
//    public List<Timesheet> getTimesheetByVolunteer() {
//        return null;
//    }
//
//    @Override
//    public List<Timesheet> getTimesheetByAssignment() {
//        return null;
//    }
}
