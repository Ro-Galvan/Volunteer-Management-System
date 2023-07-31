package org.vms.volunteer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.vms.volunteer.dto.Assignment;
import org.vms.volunteer.dto.Nonprofit;
import org.vms.volunteer.dto.Skill;
import org.vms.volunteer.dto.Volunteer;
import org.vms.volunteer.service.SkillService;
import org.vms.volunteer.service.VolunteerService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class SkillController {
    @Autowired
    SkillService skillService;

    @Autowired
    VolunteerService volunteerService;

//   The ConstraintViolation object holds information about the error; specifically, each one will hold the message of a
//    validation error it found. In this situation, we would send the full set to the page to process like a list, printing out the errors.
    Set<ConstraintViolation<Skill>> violations = new HashSet<>();

    @GetMapping("skills")
    public String displaySkills(Model model) {
        List<Skill> skills = skillService.getAllSkills();
        List<Volunteer> volunteers = volunteerService.getAllVolunteers();

        model.addAttribute("skills", skills);
        model.addAttribute("volunteers", volunteers);
        //    added this for validations
        model.addAttribute("errors", violations);

        return "skills";
    }

    //              **************ADD skill*************
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

        //        We instantiate our Validator object.
//        We then pass the full Skill object into the Validator and save the results in a “violations” class variable.
//        We then check if we found any validation errors; if not, we add the Skill.
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(skill);

        if(violations.isEmpty()) {
            skillService.addSkill(skill);
            return "redirect:/skills";
        }
        return "redirect:/skills";
    }

    //              **************EDIT Skill*************
    @GetMapping("editSkill")
    public String editSkill(Integer id, Model model) {
//        get Skill as well as the lists of volunteers to display all of them for the Edit
        Skill skill = skillService.getSkillByID(id);
        List<Volunteer> volunteers = volunteerService.getAllVolunteers();

        //adds Skill selected by ID object, volunteers as an attribute to model to display to web
        model.addAttribute("skill", skill);
        model.addAttribute("volunteers", volunteers);
        //  added this for validations
        model.addAttribute("errors", violations);

        return "editSkill";
    }

    @PostMapping("editSkill")
    public String performEditSkill(Integer id, HttpServletRequest request) {
        Skill skill = skillService.getSkillByID(id);
//         pull out the volunteerIDs data from the HttpServletRequest
        String volunteerIDs = request.getParameter("volunteerID");

        skill.setTitle(request.getParameter("title"));
        skill.setAdditionalInfo(request.getParameter("additionalInfo"));
        skill.setVolunteer(volunteerService.getVolunteerByID(Integer.parseInt(volunteerIDs)));

        //   added for validations
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(skill);

        if(violations.isEmpty()) {
            skillService.updateSkill(skill);
            return "redirect:/skills";
        }
        return "redirect:/editSkill?id="+id;
    }

    @GetMapping("deleteSkill")
    public String deleteSkill(Integer id) {
        skillService.deleteSkillByID(id);
        return "redirect:/skills";
    }

    //              **************Search by volunteer*************
    @GetMapping("skillsByVolunteer")
    public String getSkillsByVolunteer(@RequestParam Integer volunteerID, Model model, HttpServletRequest request) {
        Volunteer volunteer = volunteerService.getVolunteerByID(volunteerID);

        List<Skill> skills = skillService.getSkillsByVolunteer(volunteer);
// if I keep the original method in DAO I can add the for loop here to populate volunteer name but better to keep this in dao or service layer for code reusability
//        for (Skill skill : skills) {
//            skill.setVolunteer(volunteer);
//        }
        model.addAttribute("skills", skills);

        List<Volunteer> volunteers = volunteerService.getAllVolunteers();
        model.addAttribute("volunteers", volunteers);

        return "skills";
    }
}
