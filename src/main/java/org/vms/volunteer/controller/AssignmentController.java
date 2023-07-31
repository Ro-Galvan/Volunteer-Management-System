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
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class AssignmentController {
    @Autowired
    AssignmentService assignmentService;

    @Autowired
    NonprofitService nonprofitService;

//    The ConstraintViolation object holds information about the error; specifically, each one will hold the message of a
//    validation error it found. In this situation, we would send the full set to the page to process like a list, printing out the errors.
//   ******* FYI ******  makes the violations set shared among different requests, leading to unexpected behavior.
    Set<ConstraintViolation<Assignment>> violations = new HashSet<>();

    @GetMapping("assignments")
    public String displayAssignments(Model model) {
        List<Assignment> assignments = assignmentService.getAllAssignments();
        List<Nonprofit> nonprofits = nonprofitService.getAllNonprofits();

//        adding lists of assignments and nonprofits
        model.addAttribute("assignments", assignments);
        model.addAttribute("nonprofits", nonprofits);

        //   added this for validations
        model.addAttribute("errors", violations);

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
        assignment.setNonprofit(nonprofitService.getNonprofitByID(Integer.parseInt(nonprofitId)));

        model.addAttribute("assignment", assignment);

        //        We instantiate our Validator object.
//        We then pass the full Assignment object into the Validator and save the results in a “violations” class variable.
//        We then check if we found any validation errors; if not, we add the Assignment.
//        ADDED FOR VALIDATIONS
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(assignment);

        if(violations.isEmpty()) {
            assignmentService.addAssignment(assignment);
            return "redirect:/assignments";
        }
        return "redirect:/assignments";
    }

//                  **************EDIT Assignment*************
    @GetMapping("editAssignment")
    public String editAssignment(Integer id, Model model) {
//        get Assignment as well as the lists of nonprofits to display all of them for the Edit
        Assignment assignment = assignmentService.getAssignmentByID(id);
        List<Nonprofit> nonprofits = nonprofitService.getAllNonprofits();

        //adds Assignment & nonprofits selected by ID object as an attribute to model to display to web
        model.addAttribute("assignment", assignment);
        model.addAttribute("nonprofits", nonprofits);
        //  added this for validations
        model.addAttribute("errors", violations);

        return "editAssignment";
    }

    @PostMapping("editAssignment")
    public String performEditAssignment(Integer id, HttpServletRequest request) {
        Assignment assignment = assignmentService.getAssignmentByID(id);
//         pull out the nonprofitIDs data from the HttpServletRequest
        String nonprofitIDs = request.getParameter("nonprofitID");

        assignment.setTitle(request.getParameter("title"));
        assignment.setAdditionalInfo(request.getParameter("additionalInfo"));
        assignment.setDate(LocalDate.parse(request.getParameter("date")));
        assignment.setNonprofit(nonprofitService.getNonprofitByID(Integer.parseInt(nonprofitIDs)));

        //  added for validations
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(assignment);

        if(violations.isEmpty()) {
            assignmentService.updateAssignment(assignment);
            return "redirect:/assignments";
        }
        return "redirect:/editAssignment?id="+id;
    }

    @GetMapping("deleteAssignment")
    public String deleteAssignment(Integer id) {
        assignmentService.deleteAssignmentByID(id);
        return "redirect:/assignments";
    }
}
