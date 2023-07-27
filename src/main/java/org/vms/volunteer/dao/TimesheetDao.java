package org.vms.volunteer.dao;



import org.vms.volunteer.dto.Timesheet;

import java.util.List;

public interface TimesheetDao {
    Timesheet addTimesheet(Timesheet timesheet);
    Timesheet getTimesheetByID(int id);
    List<Timesheet> getAllTimesheets();
    void updateTimesheet(Timesheet timesheet);
    void deleteTimesheetByID(int id);


//    List<Timesheet> getTimesheetByVolunteer();
//    List<Timesheet> getTimesheetByAssignment();
}
