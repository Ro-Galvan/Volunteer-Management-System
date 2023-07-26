package org.vms.volunteer.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.vms.volunteer.dto.Skill;
import org.vms.volunteer.dto.Volunteer;
import org.vms.volunteer.mapper.SkillMapper;
import org.vms.volunteer.mapper.VolunteerMapper;

import java.util.List;
@Repository
public class SkillDaoDB implements SkillDao{
    @Autowired
    JdbcTemplate jdbc;
    @Override
    public Skill addSkill(Skill skill) {
        //executes the SQL statement to insert new skill, using provided values from the Skill object
        final String SQL = "INSERT INTO skill(title, additionalInfo, volunteerID) " + "VALUES(?,?,?)";
        jdbc.update(SQL,
                skill.getTitle(),
                skill.getAdditionalInfo(),
                skill.getVolunteer().getId());

        //retrieves the last inserted auto-generated ID of the newly inserted skill
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        //sets the ID of the object to the newly generated ID
        skill.setId(newId);
//        TODO added a private method
        Volunteer volunteer = getVolunteerForSkill(skill.getVolunteer().getId());
        skill.setVolunteer(volunteer);
        //return the modified Skill object
        return skill;
    }

    private Volunteer getVolunteerForSkill(int volunteerId) {
        try{
//            TODO I have a similar SQL statement in Volunteer as a bonus method-not sure if that one is needed anymore
            final String GET_VOLUNTEER_BY_ID = "v.firstName, v.lastName, v.city, v.state, s.title , s.additionalInfo " +
                    "FROM Volunteer v " +
                    "LEFT JOIN Skill s ON v.volunteerID = s.volunteerID " +
                    "WHERE v.volunteerID = ?;";
            Volunteer volunteer = jdbc.queryForObject(GET_VOLUNTEER_BY_ID, new VolunteerMapper(), volunteerId);
            return volunteer;
        }
        catch (DataAccessException ex){
            return null;
        }
    }

//TODO not sure if this is right
    @Override
    public Skill getSkillByID(int id) {
        try {
            final String SQL = "SELECT * FROM skill WHERE skillID = ?";

            Skill skill = jdbc.queryForObject(SQL, new SkillMapper(), id);
            //executes SQL query and maps result to a Volunteer object using the VolunteerMapper class

            Volunteer volunteer = getVolunteerForSkill(skill.getVolunteer().getId());
            skill.setVolunteer(volunteer);

            return skill;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Skill> getAllSkills() {
        try{
            final String sql = "SELECT * FROM skill";
            List<Skill> list = jdbc.query(sql, new SkillMapper());

//        added this
            for (Skill skill : list){
                skill.setVolunteer(getVolunteerForSkill(skill.getId()));
            }
            return list;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public void updateSkill(Skill skill) {

    }

    @Override
    public void deleteSkillByID(int id) {

    }

    @Override
    public List<Skill> getSkillsByVolunteer(Volunteer volunteer) {
        return null;
    }
}
