package org.vms.volunteer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vms.volunteer.dao.SkillDao;
import org.vms.volunteer.dto.Skill;
import org.vms.volunteer.dto.Volunteer;

import java.util.List;

@Service
public class SkillServiceImpl implements SkillService{
    @Autowired
    SkillDao skillDao;

    @Override
    public Skill addSkill(Skill skill) {
        return skillDao.addSkill(skill);
    }

    @Override
    public Skill getSkillByID(int id) {
        return skillDao.getSkillByID(id);
    }

    @Override
    public List<Skill> getAllSkills() {
        return skillDao.getAllSkills();
    }

    @Override
    public void updateSkill(Skill skill) {
        skillDao.updateSkill(skill);
    }

    @Override
    public void deleteSkillByID(int id) {
        skillDao.deleteSkillByID(id);
    }

    @Override
    public List<Skill> getSkillsByVolunteer(Volunteer volunteer) {
        return skillDao.getSkillsByVolunteer(volunteer);
    }
}
