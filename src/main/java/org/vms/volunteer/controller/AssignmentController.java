package org.vms.volunteer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.vms.volunteer.dto.Assignment;
import org.vms.volunteer.dto.Nonprofit;
import org.vms.volunteer.dto.Skill;
import org.vms.volunteer.dto.Volunteer;
import org.vms.volunteer.service.AssignmentService;
import org.vms.volunteer.service.NonprofitService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;

@Controller
public class AssignmentController {
    @Autowired
    AssignmentService assignmentService;

    @Autowired
    NonprofitService nonprofitService;

    @GetMapping("assignments")
    public String displayAssignments(Model model) {
        List<Assignment> assignments = assignmentService.getAllAssignments();
        List<Nonprofit> nonprofits = nonprofitService.getAllNonprofits();

//        adding lists of assignments and nonprofits
        model.addAttribute("assignments", assignments);
        model.addAttribute("nonprofits", nonprofits);

        return "assignments";
    }

    @PostMapping("addAssignment")
    public String addAssignment(HttpServletRequest request, Model model) {
//     pull out the nonprofitIds data from the HttpServletRequest
        String nonprofitId = request.getParameter("nonprofitId");


//        take in an Assignment object that captures the fields properties/fields, use HttpServletRequest object to capture those fields
        Assignment assignment = new Assignment();
        assignment.setTitle(request.getParameter("title"));
        assignment.setAdditionalInfo(request.getParameter("additionalInfo"));
        assignment.setDate(LocalDate.parse(request.getParameter("date")));

//      setting nonprofit using nonprofitService
        //TODO this will cause an error if volunteer associated with skill is left null
        assignment.setNonprofit(nonprofitService.getNonprofitByID(Integer.parseInt(nonprofitId)));


        model.addAttribute("assignment", assignment);

        assignmentService.addAssignment(assignment);

        return "redirect:/assignments";
    }

//  TODO need to add update


    @GetMapping("deleteAssignment")
    public String deleteAssignment(Integer id) {
        assignmentService.deleteAssignmentByID(id);
        return "redirect:/assignments";
    }
}
