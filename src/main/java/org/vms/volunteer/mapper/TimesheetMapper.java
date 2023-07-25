package org.vms.volunteer.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.vms.volunteer.dto.Timesheet;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class TimesheetMapper implements RowMapper<Timesheet> {
    @Override
    public Timesheet mapRow(ResultSet rs, int rowNum) throws SQLException {
        Timesheet timesheet = new Timesheet();
        timesheet.setId(rs.getInt("timesheetID"));
        timesheet.setHoursLogged(rs.getString("hoursLogged"));
        timesheet.setDate(rs.getObject("date", LocalDate.class));
        return timesheet;
    }
}
