package org.vms.volunteer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vms.volunteer.dao.TimesheetDao;
import org.vms.volunteer.dto.Timesheet;

import java.util.List;
@Service
public class TimesheetServiceImpl implements TimesheetService{
    @Autowired
    TimesheetDao timesheetDao;

    @Override
    public Timesheet addTimesheet(Timesheet timesheet) {
        return timesheetDao.addTimesheet(timesheet);
    }

    @Override
    public Timesheet getTimesheetByID(int id) {
        return timesheetDao.getTimesheetByID(id);
    }

    @Override
    public List<Timesheet> getAllTimesheets() {
        return timesheetDao.getAllTimesheets();
    }

    @Override
    public void updateTimesheet(Timesheet timesheet) {
        timesheetDao.updateTimesheet(timesheet);
    }

    @Override
    public void deleteTimesheetByID(int id) {
        timesheetDao.deleteTimesheetByID(id);
    }
}
