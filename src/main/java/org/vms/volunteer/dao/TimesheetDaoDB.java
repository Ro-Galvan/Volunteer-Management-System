package org.vms.volunteer.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.vms.volunteer.dto.Assignment;
import org.vms.volunteer.dto.Timesheet;
import org.vms.volunteer.dto.Volunteer;
import org.vms.volunteer.mapper.AssignmentMapper;
import org.vms.volunteer.mapper.TimesheetMapper;
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
//        private methods are used to get Volunteer + Assignment ids
        Volunteer volunteer = getVolunteerForTimesheet(timesheet.getVolunteer().getId());
        timesheet.setVolunteer(volunteer);
//TODO should the id passed be newID instead?
//        Assignment assignment = getAssignmentForTimesheet(timesheet.getAssignment().getId());
        Assignment assignment = getAssignmentForTimesheet(newId);
        timesheet.setAssignment(assignment);
        //return the modified timesheet object
        return timesheet;
    }

    @Override
    public Timesheet getTimesheetByID(int id) {
        try {
            final String SQL = "SELECT * FROM timesheet WHERE timesheetID = ?";

            //executes SQL query and maps result to an Assignment object using the AssignmentMapper class
            Timesheet timesheet = jdbc.queryForObject(SQL, new TimesheetMapper(), id);
//      using private methods
            timesheet.setVolunteer(getVolunteerForTimesheet(id));
            timesheet.setAssignment(getAssignmentForTimesheet(id));

            return timesheet;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    @Transactional
    public List<Timesheet> getAllTimesheets() {
        try{
            final String sql = "SELECT * FROM timesheet";
            List<Timesheet> timesheetList = jdbc.query(sql, new TimesheetMapper());


            for (Timesheet timesheet : timesheetList){
                timesheet.setVolunteer(getVolunteerForTimesheet(timesheet.getId()));
                timesheet.setAssignment(getAssignmentForTimesheet(timesheet.getId()));

            }
            return timesheetList;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public void updateTimesheet(Timesheet timesheet) {
        final String SQL = "UPDATE timesheet SET hoursLogged = ?, date = ?, volunteerID = ?, assignmentID = ? WHERE timesheetID = ? ";
        jdbc.update(SQL,
                timesheet.getHoursLogged(),
                timesheet.getDate(),
                timesheet.getVolunteer().getId(),
                timesheet.getAssignment().getId(),
                timesheet.getId());
    }

    @Override
    public void deleteTimesheetByID(int id) {
        final String DELETE = "DELETE FROM timesheet WHERE timesheetID = ?";
        jdbc.update(DELETE, id);
    }

    //    *****PRIVATE HELPER METHODS***

    private Volunteer getVolunteerForTimesheet(int volunteerId) {
        try{
            //With an INNER JOIN, only the skills that have a matching volunteerID in the volunteer table will be included in the result set.
//            TODO need to update the SQL query to use timesheet table instead of Skill
            final String SELECT_VOLUNTEER_FOR_TIMESHEET = "SELECT v.* FROM timesheet t JOIN volunteer v ON t.volunteerID = v.volunteerID WHERE t.timesheetID = ?;";
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
        try{
            //With an INNER JOIN, only the Timesheets that have a matching assignmentID in the Assignment table will be included in the result set.
            final String SELECT_ASSIGNMENT_FOR_TIMESHEET = "SELECT a.* FROM timesheet t JOIN assignment a ON t.assignmentID = a.assignmentID WHERE t.timesheetID = ?;";
//            what I had earlier that was wrong:
//            final String SELECT_VOLUNTEER_FOR_SKILL = "SELECT v.* FROM skill s JOIN volunteer v ON s.volunteerID = v.volunteerID WHERE s.volunteerID = ?;";

//            JDBC queryForObject brings back 1 row and wrapped in a try catch but query for doesn't
            Assignment assignment = jdbc.queryForObject(SELECT_ASSIGNMENT_FOR_TIMESHEET, new AssignmentMapper(), id);
            return assignment;
        }
        catch (DataAccessException ex){
            return null;
        }
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
