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
import java.time.LocalDate;
import java.util.List;

@Controller
public class TimesheetController {
    @Autowired
    TimesheetService timesheetService;

    @Autowired
    VolunteerService volunteerService;

    @Autowired
    AssignmentService assignmentService;



    @GetMapping("timesheets")
    public String displayTimesheets(Model model) {
        List<Timesheet> timesheets = timesheetService.getAllTimesheets();
        List<Volunteer> volunteers = volunteerService.getAllVolunteers();
        List<Assignment> assignments = assignmentService.getAllAssignments();

//        adding lists of timesheets, assignments and volunteers
        model.addAttribute("timesheets", timesheets);
        model.addAttribute("volunteers", volunteers);
        model.addAttribute("assignments", assignments);

        return "timesheets";
    }

    @PostMapping("addTimesheet")
    public String addTimesheet(HttpServletRequest request, Model model) {
//     pull out the volunteerIds & assignmentIds data from the HttpServletRequest
        String volunteerId = request.getParameter("volunteerId");
        String assignmentId = request.getParameter("assignmentId");

//        take in an Timesheet object that captures the fields properties/fields, use HttpServletRequest object to capture those fields
        Timesheet timesheet = new Timesheet();
        timesheet.setHoursLogged(request.getParameter("title"));
        timesheet.setDate(LocalDate.parse(request.getParameter("date")));

//      setting volunteer &  assignment using their respective service layer
        timesheet.setVolunteer(volunteerService.getVolunteerByID(Integer.parseInt(volunteerId)));
        timesheet.setAssignment(assignmentService.getAssignmentByID(Integer.parseInt(assignmentId)));

        model.addAttribute("timesheet", timesheet);

        timesheetService.addTimesheet(timesheet);

        return "redirect:/timesheets";
    }

//  TODO need to add update


    //                  **************EDIT Timesheet*************
//    @GetMapping("editTimesheet")
//    public String editAssignment(Integer id, Model model) {
////        get Timesheet  as well as the lists of Volunteers & Assignments to display all of them for the Edit
//        Assignment assignment = assignmentService.getAssignmentByID(id);
//        List<Nonprofit> nonprofits = nonprofitService.getAllNonprofits();
//
//        //adds Assignment selected by ID object, nonprofits as an attribute to model to display to web
//        model.addAttribute("assignment", assignment);
//        model.addAttribute("nonprofits", nonprofits);
//
//        return "editTimesheet";
//    }
//
//    @PostMapping("editTimesheet")
//    public String performEditTimesheet(Integer id, HttpServletRequest request) {
//        Assignment assignment = assignmentService.getAssignmentByID(id);
////         pull out the nonprofitIDs data from the HttpServletRequest
//        String nonprofitIDs = request.getParameter("nonprofitID");
//
//        assignment.setTitle(request.getParameter("title"));
//        assignment.setAdditionalInfo(request.getParameter("additionalInfo"));
//        assignment.setDate(LocalDate.parse(request.getParameter("date")));
//        assignment.setNonprofit(nonprofitService.getNonprofitByID(Integer.parseInt(nonprofitIDs)));
//
//        assignmentService.updateAssignment(assignment);
//        return "redirect:/timesheets";
//    }


    @GetMapping("deleteTimesheet")
    public String deleteTimesheet(Integer id) {
        timesheetService.deleteTimesheetByID(id);
        return "redirect:/timesheets";
    }
}
