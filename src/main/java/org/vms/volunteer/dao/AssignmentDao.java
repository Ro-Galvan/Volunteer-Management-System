package org.vms.volunteer.dao;

import org.vms.volunteer.dto.Assignment;

import java.util.List;

public interface AssignmentDao {
    Assignment addAssignment(Assignment assignment);
    Assignment getAssignmentByID(int id);
    List<Assignment> getAllAssignments();
    void updateAssignment(Assignment assignment);
    void deleteAssignmentByID(int id);
}
