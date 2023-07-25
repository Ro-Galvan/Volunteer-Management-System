package org.vms.volunteer.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.vms.volunteer.dto.Nonprofit;

import java.sql.ResultSet;
import java.sql.SQLException;
public class NonprofitMapper implements RowMapper<Nonprofit> {
    @Override
    public Nonprofit mapRow(ResultSet rs, int rowNum) throws SQLException {
        Nonprofit nonprofit = new Nonprofit();
        nonprofit.setId(rs.getInt("nonprofitID"));
        nonprofit.setCompanyName(rs.getString("companyName"));
        nonprofit.setPhoneNumber(rs.getString("phoneNum"));
        nonprofit.setEmail(rs.getString("email"));
        nonprofit.setAddress(rs.getString("address"));
        nonprofit.setMission(rs.getString("mission"));
        return nonprofit;
    }
}
