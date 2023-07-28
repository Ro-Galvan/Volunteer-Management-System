package org.vms.volunteer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vms.volunteer.dao.AssignmentDao;
import org.vms.volunteer.dto.Assignment;

import java.util.List;

@Service
public class AssignmentServiceImpl implements AssignmentService{
    @Autowired
    AssignmentDao assignmentDao;

    @Override
    public Assignment addAssignment(Assignment assignment) {
        return assignmentDao.addAssignment(assignment);
    }

    @Override
    public Assignment getAssignmentByID(int id) {
        return assignmentDao.getAssignmentByID(id);
    }

    @Override
    public List<Assignment> getAllAssignments() {
        return assignmentDao.getAllAssignments();
    }

    @Override
    public void updateAssignment(Assignment assignment) {
        assignmentDao.updateAssignment(assignment);
    }

    @Override
    public void deleteAssignmentByID(int id) {
        assignmentDao.deleteAssignmentByID(id);
    }
}
