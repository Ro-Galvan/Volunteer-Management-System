package org.vms.volunteer.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.vms.volunteer.dto.Volunteer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class VolunteerMapper implements RowMapper<Volunteer> {
    @Override
    public Volunteer mapRow(ResultSet rs, int rowNum) throws SQLException {
        Volunteer volunteer = new Volunteer();
        volunteer.setId(rs.getInt("volunteerID"));
        volunteer.setFirstName(rs.getString("firstName"));
        volunteer.setLastName(rs.getString("lastName"));
        volunteer.setPhoneNumber(rs.getString("phoneNum"));
        volunteer.setEmail(rs.getString("email"));
        volunteer.setCity(rs.getString("city"));
        volunteer.setState(rs.getString("state"));
        return volunteer;
    }
}
