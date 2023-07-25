package org.vms.volunteer.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.vms.volunteer.dto.Assignment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class AssignmentMapper implements RowMapper<Assignment> {

    @Override
    public Assignment mapRow(ResultSet rs, int rowNum) throws SQLException {
        Assignment assignment = new Assignment();
        assignment.setId(rs.getInt("assignmentID"));
        assignment.setTitle(rs.getString("title"));
        assignment.setAdditionalInfo(rs.getString("additionalInfo"));
        assignment.setDate(rs.getObject("date", LocalDate.class));
        return assignment;
    }
}
