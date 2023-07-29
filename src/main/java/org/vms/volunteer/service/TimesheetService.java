package org.vms.volunteer.service;

import org.vms.volunteer.dto.Timesheet;

import java.util.List;

public interface TimesheetService {
    Timesheet addTimesheet(Timesheet timesheet);
    Timesheet getTimesheetByID(int id);
    List<Timesheet> getAllTimesheets();
    void updateTimesheet(Timesheet timesheet);
    void deleteTimesheetByID(int id);
}
