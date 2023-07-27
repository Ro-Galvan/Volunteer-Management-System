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

        // Map the volunteer's first name and last name
        /*
        retrieve the firstName and lastName columns from the ResultSet and check if both are not null. If they are
        not null, we create a new Volunteer object, set the first name and last name, and then associate it with the
        skill using skill.setVolunteer(volunteer)
         */
        String firstName = rs.getString("firstName");
        String lastName = rs.getString("lastName");
        if (firstName != null && lastName != null) {
            Volunteer volunteer = new Volunteer();
            volunteer.setFirstName(firstName);
            volunteer.setLastName(lastName);
            skill.setVolunteer(volunteer);
        }
        return skill;
    }
}
