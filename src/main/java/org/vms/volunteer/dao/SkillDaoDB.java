package org.vms.volunteer.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional
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

//    *****PRIVATE HELPER METHOD***
    private Volunteer getVolunteerForSkill(int volunteerId) {
        try{
//            TODO I have a similar SQL statement in Volunteer as a bonus method-not sure if that one is needed anymore
            final String SELECT_VOLUNTEER_FOR_SKILL = "v.firstName, v.lastName, v.city, v.state, s.title AS skillTitle, s.additionalInfo " +
                    "FROM Volunteer v " +
                    "LEFT JOIN Skill s ON v.volunteerID = s.volunteerID " +
                    "WHERE v.volunteerID = ?;";
            Volunteer volunteer = jdbc.queryForObject(SELECT_VOLUNTEER_FOR_SKILL, new VolunteerMapper(), volunteerId);
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
//          TODO might have to try this way if it doesn't work:

//            Volunteer volunteer = getVolunteerForSkill(skill.getVolunteer().getId());
//            skill.setVolunteer(volunteer);

            skill.setVolunteer(getVolunteerForSkill(id));


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


            for (Skill skill : list){
                skill.setVolunteer(getVolunteerForSkill(skill.getId()));
            }
            return list;
        } catch (DataAccessException ex) {
            return null;
        }
    }

//    TODO have to see if it works--
//     there is no need to delete anything for an update since skillID isn't a FK anywhere
    @Override
    public void updateSkill(Skill skill) {
        final String UPDATE_SKILL = "UPDATE skill SET title = ?, additionalInfo = ?, volunteerID = ? "
                + "WHERE id = ?";
        jdbc.update(UPDATE_SKILL,
                skill.getTitle(),
                skill.getAdditionalInfo(),
                skill.getVolunteer().getId(),
                skill.getId());
    }

    @Override
    public void deleteSkillByID(int id) {
        final String DELETE_SKILL = "DELETE FROM skill WHERE id = ?";
        jdbc.update(DELETE_SKILL, id);
    }


//    TODO do I need the below??
    @Override
    public List<Skill> getSkillsByVolunteer(Volunteer volunteer) {
        final String SQL = "SELECT v.volunteerID, v.firstName, v.lastName, v.phoneNum, v.email, v.city, v.state, s.skillID, s.title AS skillTitle, s.additionalInfo " +
                "FROM Volunteer " +
                "LEFT JOIN Skill s ON v.volunteerID = s.volunteerID;";
        return jdbc.query(SQL, new SkillMapper(), volunteer.getId());
    }
}
