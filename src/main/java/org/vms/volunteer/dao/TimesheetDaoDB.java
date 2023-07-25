package org.vms.volunteer.dao;

import org.vms.volunteer.dto.Timesheet;

import java.util.List;

public class TimesheetDaoDB implements TimesheetDao{
    @Override
    public Timesheet addTimesheet(Timesheet timesheet) {
        return null;
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

    @Override
    public List<Timesheet> getTimesheetByVolunteer() {
        return null;
    }

    @Override
    public List<Timesheet> getTimesheetByAssignment() {
        return null;
    }
}
