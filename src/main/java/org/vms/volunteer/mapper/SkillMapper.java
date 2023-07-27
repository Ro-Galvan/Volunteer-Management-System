package org.vms.volunteer.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.vms.volunteer.dto.Skill;
import org.vms.volunteer.dto.Volunteer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SkillMapper implements RowMapper<Skill> {
    @Override
    public Skill mapRow(ResultSet rs, int rowNum) throws SQLException {
        Skill skill = new Skill();
        skill.setId(rs.getInt("skillID"));
        skill.setTitle(rs.getString("title"));
        skill.setAdditionalInfo(rs.getString("additionalInfo"));

        return skill;
    }
}
