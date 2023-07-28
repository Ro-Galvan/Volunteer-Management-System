package org.vms.volunteer.dao;

import org.vms.volunteer.dto.Assignment;
import org.vms.volunteer.dto.Nonprofit;

import java.util.List;
//TODO One assignment can have multiple timesheets (one-to-many relationship)
public interface AssignmentDao {
    Assignment addAssignment(Assignment assignment);
    Assignment getAssignmentByID(int id);
    List<Assignment> getAllAssignments();
    void updateAssignment(Assignment assignment);
    void deleteAssignmentByID(int id);


    //    extra method to show the assignments by nonprofits ON THE DETAILS PAGE????
    //    TODO I think in hero project it was only used in testing:-- IS IT REALLY NEEDED??????
//    List<Assignment> getAssignmentsByNonprofit(Nonprofit nonprofit);
}
