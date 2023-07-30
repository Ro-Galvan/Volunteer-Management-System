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
//        using private method
        Volunteer volunteer = getVolunteerForSkill(newId); // TODO had to change to get the tests to pass
//        Volunteer volunteer = getVolunteerForSkill(skill.getVolunteer().getId());
        skill.setVolunteer(volunteer);
        //return the modified Skill object
        return skill;
    }

//    *****PRIVATE HELPER METHOD***
//    TODO needs to be a skill ID not volunteer
    private Volunteer getVolunteerForSkill(int volunteerId) {
        try{
//            TODO I have a similar SQL statement in Volunteer as a bonus method-not sure if that one is needed anymore
            //With an INNER JOIN, only the skills that have a matching volunteerID in the volunteer table will be included in the result set.
            final String SELECT_VOLUNTEER_FOR_SKILL = "SELECT v.* FROM skill s JOIN volunteer v ON s.volunteerID = v.volunteerID WHERE s.skillID = ?;";
//            what I had earlier that was wrong:
//            final String SELECT_VOLUNTEER_FOR_SKILL = "SELECT v.* FROM skill s JOIN volunteer v ON s.volunteerID = v.volunteerID WHERE s.volunteerID = ?;";

//            JDBC queryForObject brings back 1 row and wrapped in a try catch but query for doesn't
            Volunteer volunteer = jdbc.queryForObject(SELECT_VOLUNTEER_FOR_SKILL, new VolunteerMapper(), volunteerId);
            return volunteer;
        }
        catch (DataAccessException ex){
            return null;
        }
    }

    @Override
    public Skill getSkillByID(int id) {
        try {
            final String SQL = "SELECT * FROM skill WHERE skillID = ?";

            //executes SQL query and maps result to a Volunteer object using the VolunteerMapper class
            Skill skill = jdbc.queryForObject(SQL, new SkillMapper(), id);
//          TODO might have to try this way if it doesn't work:

//            Volunteer volunteer = getVolunteerForSkill(skill.getVolunteer().getId());
//            skill.setVolunteer(volunteer);

            skill.setVolunteer(getVolunteerForSkill(id));


            return skill;
        } catch (DataAccessException ex) {
            return null;
        }
    }

// Below method fetches the skills from the database and then separately fetches the volunteer for each skill using
// the getVolunteerForSkill(skill.getId()) method.
    @Override
    public List<Skill> getAllSkills() {
        try{
            final String sql = "SELECT * FROM skill";
            List<Skill> skillsList = jdbc.query(sql, new SkillMapper());


            for (Skill skill : skillsList){
                skill.setVolunteer(getVolunteerForSkill(skill.getId()));
            }
            return skillsList;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public void updateSkill(Skill skill) {
        final String UPDATE_SKILL = "UPDATE skill SET title = ?, additionalInfo = ?, volunteerID = ? "
                + "WHERE skillID = ?";
        jdbc.update(UPDATE_SKILL,
                skill.getTitle(),
                skill.getAdditionalInfo(),
                skill.getVolunteer().getId(),
                skill.getId());
    }

    @Override
    public void deleteSkillByID(int id) {
        final String DELETE_SKILL = "DELETE FROM skill WHERE skillID = ?";
        jdbc.update(DELETE_SKILL, id);
    }


//    This is for the search feature to filter by volunteer
//    *************original method but without the for loop on skills to set volunteer it didn't populate volunteer name column on front end
//    the original method didn't have the for loop
    @Override
    public List<Skill> getSkillsByVolunteer(Volunteer volunteer) {
//        simpler and easier to understand, separates the queries for skills and volunteers.
        final String SQL = "SELECT * FROM skill WHERE volunteerID = ?;";
//        this way works too but involves a more complex SQL query harder to read and maintain, especially if there are more tables or complex relationships involved
//        final String SQL = "SELECT s.*, v.firstName, v.lastName FROM skill s " +
//                "JOIN Volunteer v ON s.volunteerID = v.volunteerID " +
//                "WHERE s.volunteerID = ?;";

        List<Skill> skills = jdbc.query(SQL, new SkillMapper(), volunteer.getId());

        for (Skill skill : skills) {
            skill.setVolunteer(volunteer);
        }

        return skills;
    }

}
