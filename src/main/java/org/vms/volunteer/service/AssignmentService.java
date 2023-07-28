package org.vms.volunteer.service;

import org.vms.volunteer.dto.Assignment;

import java.util.List;

public interface AssignmentService {
    Assignment addAssignment(Assignment assignment);
    Assignment getAssignmentByID(int id);
    List<Assignment> getAllAssignments();
    void updateAssignment(Assignment assignment);
    void deleteAssignmentByID(int id);
}
