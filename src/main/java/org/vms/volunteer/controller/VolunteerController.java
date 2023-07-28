package org.vms.volunteer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.vms.volunteer.dto.Nonprofit;
import org.vms.volunteer.dto.Volunteer;
import org.vms.volunteer.service.NonprofitService;
import org.vms.volunteer.service.VolunteerService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class VolunteerController {
    @Autowired
    VolunteerService volunteerService;

    @Autowired
    NonprofitService nonprofitService;

    /**
     * We start by making our @GetMapping for volunteers.
     * We then bring in the Model to send data to the page.
     * Next, we pull in our list of all Volunteers and add it to the Model.
     * Finally, we return volunteers, which will point us at the volunteers.html page
     * @param model
     * @return
     */

    @GetMapping("volunteers")
    public String displayVolunteers(Model model) {
        List<Volunteer> volunteers = volunteerService.getAllVolunteers();
        List<Nonprofit> nonprofits = nonprofitService.getAllNonprofits();

        model.addAttribute("volunteers", volunteers);
        model.addAttribute("nonprofits", nonprofits);
        return "volunteers";
    }
//     * Once in the method, we use the HttpServletRequest to retrieve the fields from the form based on the name we set in the HTML for each input.
//    * Once we have the data, we create the Volunteer object and fill it with the data.
//    Once we've finished creating the Volunteer, we use our volunteerService to save it to the database.
//    Finally, we redirect our browser back to the volunteers page.
    @PostMapping("addVolunteer")
    public String addVolunteer(HttpServletRequest request, Model model) {
        //        use the getParameterValues method to get a string array of nonprofitIDs
        String[] nonprofitIDs = request.getParameterValues("nonprofitID");

        Volunteer volunteer = new Volunteer();
        volunteer.setFirstName(request.getParameter("firstName"));
        volunteer.setLastName(request.getParameter("lastName"));
        volunteer.setPhoneNumber(request.getParameter("phoneNumber"));
        volunteer.setEmail(request.getParameter("email"));
        volunteer.setCity(request.getParameter("city"));
        volunteer.setState(request.getParameter("state"));

        model.addAttribute("volunteer", volunteer);

//   create an empty list of nonprofits, loop through the nonprofitIDs, retrieve each nonprofit, and add it to the list.
        List<Nonprofit> nonprofitArrayList = new ArrayList<>();
        for(String nonprofitID : nonprofitIDs) {
            nonprofitArrayList.add(nonprofitService.getNonprofitByID(Integer.parseInt(nonprofitID)));
        }
        volunteer.setNonprofits(nonprofitArrayList);

        volunteerService.addVolunteer(volunteer);

        return "redirect:/volunteers";
    }

    //              **************EDIT Volunteer*************
    @GetMapping("editVolunteer")
    public String editVolunteer(Integer id, Model model) {
        Volunteer volunteer = volunteerService.getVolunteerByID(id);
        List<Nonprofit> nonprofits = nonprofitService.getAllNonprofits();
//todo don't have the same as hero but don't think I need it
        //        for(Nonprofit nonprofit : nonprofits) {
//            nonprofit.setCompanyName(null);
//        }

        //adds volunteer selected by ID object, nonprofits as an attribute to model to display to web
        model.addAttribute("volunteer", volunteer);
        model.addAttribute("nonprofits", nonprofits);

        return "editVolunteer";
    }
    @PostMapping("editVolunteer")
    public String performEditVolunteer(Integer id, HttpServletRequest request) {
        Volunteer volunteer = volunteerService.getVolunteerByID(id);
//        use the getParameterValues method to get a string array of nonprofitIDs
        String[] nonprofitIDs = request.getParameterValues("nonprofitID");

        volunteer.setFirstName(request.getParameter("firstName"));
        volunteer.setLastName(request.getParameter("lastName"));
        volunteer.setPhoneNumber(request.getParameter("phoneNumber"));
        volunteer.setEmail(request.getParameter("email"));
        volunteer.setCity(request.getParameter("city"));
        volunteer.setState(request.getParameter("state"));

        List<Nonprofit> nonprofitList = new ArrayList<>();
        for (String nonprofitID : nonprofitIDs) {
            nonprofitList.add(nonprofitService.getNonprofitByID(Integer.parseInt(nonprofitID)));
        }
        volunteer.setNonprofits(nonprofitList);

        volunteerService.updateVolunteer(volunteer);
        return "redirect:/volunteers";
    }

    @GetMapping("deleteVolunteer")
    public String deleteVolunteer(Integer id) {
        volunteerService.deleteVolunteerByID(id);
        return "redirect:/volunteers";
    }

}
