package org.vms.volunteer.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.vms.volunteer.dto.Assignment;
import org.vms.volunteer.dto.Nonprofit;
import org.vms.volunteer.mapper.AssignmentMapper;
import org.vms.volunteer.mapper.NonprofitMapper;


import java.util.List;
@Repository
public class AssignmentDaoDB implements AssignmentDao{
    @Autowired
    JdbcTemplate jdbc;

    @Override
    @Transactional
    public Assignment addAssignment(Assignment assignment) {
        //executes the SQL statement to insert new assignment, using provided values from the Assignment object
        final String SQL = "INSERT INTO assignment(title, additionalInfo, date, nonprofitID) " + "VALUES(?,?,?,?)";
        jdbc.update(SQL,
                assignment.getTitle(),
                assignment.getAdditionalInfo(),
                assignment.getDate(),
                assignment.getNonprofit().getId());

        //retrieves the last inserted auto-generated ID of the newly inserted assignment
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        //sets the ID of the object to the newly generated ID
        assignment.setId(newId);
//        Using private method to set Nonprofit associated with Assignment
        Nonprofit nonprofit = getNonprofitForAssignment(newId);
        assignment.setNonprofit(nonprofit);
        //return the modified Assignment object
        return assignment;
    }

    //    *****PRIVATE HELPER METHOD***
    private Nonprofit getNonprofitForAssignment(int id) {
        try{
            //With an INNER JOIN, only the Assignments that have a matching NonprofitID in the volunteer table will be included in the result set.
            final String SELECT_NONPROFIT_FOR_ASSIGNMENT = "SELECT n.* FROM assignment a JOIN nonprofit n ON a.nonprofitID = n.nonprofitID WHERE a.assignmentID = ?;";
//            what I had earlier that was wrong:
//            final String SELECT_VOLUNTEER_FOR_SKILL = "SELECT v.* FROM skill s JOIN volunteer v ON s.volunteerID = v.volunteerID WHERE s.volunteerID = ?;";

//            JDBC queryForObject brings back 1 row and wrapped in a try catch but query for doesn't
            Nonprofit nonprofit = jdbc.queryForObject(SELECT_NONPROFIT_FOR_ASSIGNMENT, new NonprofitMapper(), id);
            return nonprofit;
        }
        catch (DataAccessException ex){
            return null;
        }
    }


    @Override
    public Assignment getAssignmentByID(int id) {
        try {
            final String SQL = "SELECT * FROM assignment WHERE assignmentID = ?";

            //executes SQL query and maps result to an Assignment object using the AssignmentMapper class
            Assignment assignment = jdbc.queryForObject(SQL, new AssignmentMapper(), id);
//TODO potential issue
            assignment.setNonprofit(getNonprofitForAssignment(id));


            return assignment;
        } catch (DataAccessException ex) {
            return null;
        }
    }
    /*Below method fetches the assignments from the database and then separately fetches the Nonprofit for each assignment using
    the getNonprofitForAssignment private method.*/
    @Override
    public List<Assignment> getAllAssignments() {
        try{
            final String sql = "SELECT * FROM assignment";
            List<Assignment> assignmentList = jdbc.query(sql, new AssignmentMapper());


            for (Assignment assignment : assignmentList){
                assignment.setNonprofit(getNonprofitForAssignment(assignment.getId()));
            }
            return assignmentList;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public void updateAssignment(Assignment assignment) {
        final String UPDATE_ASSIGNMENT = "UPDATE assignment SET title = ?, additionalInfo = ?, date = ?, nonprofitID = ? "
                + "WHERE assignmentID = ?";
        jdbc.update(UPDATE_ASSIGNMENT,
                assignment.getTitle(),
                assignment.getAdditionalInfo(),
                assignment.getDate(),
                assignment.getNonprofit().getId(),
                assignment.getId());
    }

    @Override
    @Transactional
    public void deleteAssignmentByID(int id) {
        // Delete from ALL associated FK tables before deleting from Assignment table.
//        1. DELETE FROM timesheet
        final String DELETE_TIMESHEET = "DELETE FROM timesheet WHERE assignmentID = ?";
        jdbc.update(DELETE_TIMESHEET, id);
//        2. DELETE FROM Assignment
        final String DELETE_ASSIGNMENT = "DELETE FROM assignment WHERE assignmentID = ?";
        jdbc.update(DELETE_ASSIGNMENT, id);
    }

//    TODO don't think I need this but maybe for testing
//    @Override
//    public List<Assignment> getAssignmentsByNonprofit(Nonprofit nonprofit) {
//        return null;
//    }
}
