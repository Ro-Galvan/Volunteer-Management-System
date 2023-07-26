package org.vms.volunteer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.vms.volunteer.dto.Volunteer;
import org.vms.volunteer.service.VolunteerService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class VolunteerController {
    @Autowired
    VolunteerService volunteerService;

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
        model.addAttribute("volunteers", volunteers);
        return "volunteers";
    }
//     * Once in the method, we use the HttpServletRequest to retrieve the fields from the form based on the name we set in the HTML for each input.
//    * Once we have the data, we create the Volunteer object and fill it with the data.
//    Once we've finished creating the Volunteer, we use our volunteerService to save it to the database.
//    Finally, we redirect our browser back to the volunteers page.
    @PostMapping("addVolunteer")
    public String addVolunteer(HttpServletRequest request) {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String phoneNumber = request.getParameter("phoneNumber");
        String email = request.getParameter("email");
        String city = request.getParameter("city");
        String state = request.getParameter("state");

        Volunteer volunteer = new Volunteer();

        volunteer.setFirstName(firstName);
        volunteer.setLastName(lastName);
        volunteer.setPhoneNumber(phoneNumber);
        volunteer.setEmail(email);
        volunteer.setCity(city);
        volunteer.setState(state);

        volunteerService.addVolunteer(volunteer);

        return "redirect:/volunteers";
    }
    //              **************EDIT Volunteer*************
    @GetMapping("editVolunteer")
    public String editVolunteer(Integer id, Model model) {
        Volunteer volunteer = volunteerService.getVolunteerByID(id);
        model.addAttribute("volunteer", volunteer);
        return "editVolunteer";
    }
    @PostMapping("editVolunteer")
    public String performEditVolunteer(Volunteer volunteer) {
        volunteerService.updateVolunteer(volunteer);
        return "redirect:/volunteers";
    }

    @GetMapping("deleteVolunteer")
    public String deleteVolunteer(Integer id) {
        volunteerService.deleteVolunteerByID(id);
        return "redirect:/volunteers";
    }

}
