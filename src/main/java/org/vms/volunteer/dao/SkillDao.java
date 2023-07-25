package org.vms.volunteer.dao;

import org.vms.volunteer.dto.Skill;
import org.vms.volunteer.dto.Volunteer;

import java.util.List;

public interface SkillDao {
    Skill addSkill(Skill skill);
    Skill getSkillByID(int id);
    List<Skill> getAllSkills();
    void updateSkill(Skill skill);
    void deleteSkillByID(int id);


    List<Skill> getSkillsByVolunteer(Volunteer volunteer);
}
