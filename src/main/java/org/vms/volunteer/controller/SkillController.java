package org.vms.volunteer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.vms.volunteer.dto.Skill;
import org.vms.volunteer.dto.Volunteer;
import org.vms.volunteer.service.SkillService;
import org.vms.volunteer.service.VolunteerService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class SkillController {
    @Autowired
    SkillService skillService;

    @Autowired
    VolunteerService volunteerService;

    @GetMapping("skills")
    public String displaySkills(Model model) {
        List<Skill> skills = skillService.getAllSkills();
        List<Volunteer> volunteers = volunteerService.getAllVolunteers();
//        System.out.println(volunteers);
        model.addAttribute("skills", skills);
//        model.addAttribute("skills", new Skill());
        model.addAttribute("volunteers", volunteers);
        return "skills";
    }

    //              **************ADD skill-------------------This way did not work*************
//    @PostMapping("addSkill")
//    public String addSkill(Skill skill, HttpServletRequest request) {
////        getting volunteerID
//        String volunteerId = request.getParameter("volunteerId");
//        skill.setVolunteer(volunteerService.getVolunteerByID(Integer.parseInt(volunteerId)));
//
//        skillService.addSkill(skill);
//
//        return "redirect:/skills";
//    }

    @PostMapping("addSkill")
    public String addSkill(HttpServletRequest request, Model model) {
//     pull out the volunteerIds data from the HttpServletRequest
        String volunteerId = request.getParameter("volunteerId");


//        take in a skill object that captures the title, additionalInfo, volunteerId fields and an HttpServletRequest object that we use to capture those fields
        Skill skill = new Skill();
        skill.setTitle(request.getParameter("title"));
        skill.setAdditionalInfo(request.getParameter("additionalInfo"));
//      setting volunteer using volunteerService
        //TODO this will cause an error if volunteer associated with skill is left null
        skill.setVolunteer(volunteerService.getVolunteerByID(Integer.parseInt(volunteerId)));


        model.addAttribute("skill", skill);

        skillService.addSkill(skill);

        return "redirect:/skills";
    }

    @GetMapping("deleteSkill")
    public String deleteSkill(Integer id) {
        skillService.deleteSkillByID(id);
        return "redirect:/skills";
    }

}