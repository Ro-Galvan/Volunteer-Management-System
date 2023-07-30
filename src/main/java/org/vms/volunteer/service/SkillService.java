package org.vms.volunteer.service;

import org.vms.volunteer.dto.Skill;
import org.vms.volunteer.dto.Volunteer;

import java.util.List;

public interface SkillService {

    Skill addSkill(Skill skill);
    Skill getSkillByID(int id);
    List<Skill> getAllSkills();
    void updateSkill(Skill skill);
    void deleteSkillByID(int id);

    //    This is for the search feature to filter by volunteer
    List<Skill> getSkillsByVolunteer(Volunteer volunteer);
}
