package org.vms.volunteer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.vms.volunteer.dto.Assignment;
import org.vms.volunteer.dto.Nonprofit;
import org.vms.volunteer.dto.Timesheet;
import org.vms.volunteer.dto.Volunteer;
import org.vms.volunteer.service.AssignmentService;
import org.vms.volunteer.service.TimesheetService;
import org.vms.volunteer.service.VolunteerService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class TimesheetController {
    @Autowired
    TimesheetService timesheetService;

    @Autowired
    VolunteerService volunteerService;

    @Autowired
    AssignmentService assignmentService;

    //    The ConstraintViolation object holds information about the error; specifically, each one will hold the message of a
//    validation error it found. In this situation, we would send the full set to the page to process like a list, printing out the errors.
    Set<ConstraintViolation<Timesheet>> violations = new HashSet<>();


    @GetMapping("timesheets")
    public String displayTimesheets(Model model) {
        List<Timesheet> timesheets = timesheetService.getAllTimesheets();
        List<Volunteer> volunteers = volunteerService.getAllVolunteers();
        List<Assignment> assignments = assignmentService.getAllAssignments();

//        adding lists of timesheets, assignments and volunteers
        model.addAttribute("timesheets", timesheets);
        model.addAttribute("volunteers", volunteers);
        model.addAttribute("assignments", assignments);
        //   added this for validations
        model.addAttribute("errors", violations);

        return "timesheets";
    }

    @PostMapping("addTimesheet")
    public String addTimesheet(HttpServletRequest request, Model model) {
//     pull out the volunteerIds & assignmentIds data from the HttpServletRequest
        String volunteerId = request.getParameter("volunteerId");
        String assignmentId = request.getParameter("assignmentId");

//        take in an Timesheet object that captures the fields properties/fields, use HttpServletRequest object to capture those fields
        Timesheet timesheet = new Timesheet();
        timesheet.setHoursLogged(request.getParameter("hoursLogged"));
        timesheet.setDate(LocalDate.parse(request.getParameter("date")));

//      setting volunteer &  assignment using their respective service layer
        timesheet.setVolunteer(volunteerService.getVolunteerByID(Integer.parseInt(volunteerId)));
        timesheet.setAssignment(assignmentService.getAssignmentByID(Integer.parseInt(assignmentId)));

        model.addAttribute("timesheet", timesheet);

        //  ADDED FOR VALIDATIONS
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(timesheet);

        if(violations.isEmpty()) {
            timesheetService.addTimesheet(timesheet);
            return "redirect:/timesheets";
        }
        return "redirect:/timesheets";
    }

    //                  **************EDIT Timesheet*************
    @GetMapping("editTimesheet")
    public String editTimesheet(Integer id, Model model) {
//        get Timesheet  as well as the lists of Volunteers & Assignments to display all of them for the Edit
        Timesheet timesheet = timesheetService.getTimesheetByID(id);
        List<Volunteer> volunteers = volunteerService.getAllVolunteers();
        List<Assignment> assignments = assignmentService.getAllAssignments();

//        adding lists of timesheets & assignment and volunteer by ID as an attribute to model to display to web
        model.addAttribute("timesheet", timesheet);
        model.addAttribute("volunteers", volunteers);
        model.addAttribute("assignments", assignments);
        //   added this for validations
        model.addAttribute("errors", violations);

        return "editTimesheet";
    }

    @PostMapping("editTimesheet")
    public String performEditTimesheet(Integer id, HttpServletRequest request) {
        Timesheet timesheet = timesheetService.getTimesheetByID(id);
//         pull out the assignment and volunteer IDS data from the HttpServletRequest
        String volunteerIDs = request.getParameter("volunteerID");
        String assignmentIDs = request.getParameter("assignmentID");

        timesheet.setHoursLogged(request.getParameter("hoursLogged"));
        timesheet.setDate(LocalDate.parse(request.getParameter("date")));
        //      setting volunteer &  assignment using their respective service layer
        timesheet.setVolunteer(volunteerService.getVolunteerByID(Integer.parseInt(volunteerIDs)));
        timesheet.setAssignment(assignmentService.getAssignmentByID(Integer.parseInt(assignmentIDs)));

        //  TODO added for validations
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(timesheet);

        if(violations.isEmpty()) {
            timesheetService.updateTimesheet(timesheet);
            return "redirect:/timesheets";
        }
        return "redirect:/editTimesheet?id="+id;
    }


    @GetMapping("deleteTimesheet")
    public String deleteTimesheet(Integer id) {
        timesheetService.deleteTimesheetByID(id);
        return "redirect:/timesheets";
    }
}
