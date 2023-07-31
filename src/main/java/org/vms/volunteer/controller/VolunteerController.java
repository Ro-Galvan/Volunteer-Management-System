package org.vms.volunteer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.vms.volunteer.dto.Assignment;
import org.vms.volunteer.dto.Nonprofit;
import org.vms.volunteer.dto.Volunteer;
import org.vms.volunteer.service.NonprofitService;
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
public class VolunteerController {
    @Autowired
    VolunteerService volunteerService;

    @Autowired
    NonprofitService nonprofitService;

//   The ConstraintViolation object holds information about the error; specifically, each one will hold the message of a
//    validation error it found. In this situation, we would send the full set to the page to process like a list, printing out the errors.
    Set<ConstraintViolation<Volunteer>> violations = new HashSet<>();

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
        // added this for validations
        model.addAttribute("errors", violations);
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

        //   ADDED FOR VALIDATIONS
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(volunteer);

        if(violations.isEmpty()) {
            volunteerService.addVolunteer(volunteer);
            return "redirect:/volunteers";
        }
        return "redirect:/volunteers";
    }

    //              **************EDIT Volunteer*************
    @GetMapping("editVolunteer")
    public String editVolunteer(Integer id, Model model) {
        Volunteer volunteer = volunteerService.getVolunteerByID(id);
        List<Nonprofit> nonprofits = nonprofitService.getAllNonprofits();

        //adds volunteer selected by ID object, nonprofits as an attribute to model to display to web
        model.addAttribute("volunteer", volunteer);
        model.addAttribute("nonprofits", nonprofits);
        //  added  for validations
        model.addAttribute("errors", violations);

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

        //  added for validations
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(volunteer);

        if(violations.isEmpty()) {
            volunteerService.updateVolunteer(volunteer);
            return "redirect:/volunteers";
        }
        return "redirect:/editVolunteer?id="+id;
    }

    @GetMapping("deleteVolunteer")
    public String deleteVolunteer(Integer id) {
        volunteerService.deleteVolunteerByID(id);
        return "redirect:/volunteers";
    }

    //              **************Volunteer DETAIL*************

    /**
     * handles HTTP GET requests for the URL path /detailVolunteer
     * method retrieves info about a superhero & adds this data to the Model.
     * The Thymeleaf template, "detailHero.html," will use this data to render the details of the superhero on the web page.
     * @param id  identifies the specific hero for which the details will be fetched
     * @param model Spring Model object that allows you to pass data from the controller to the view (Thymeleaf template) for rendering.
     * @return method returns the String "detailHero". This indicates that the Thymeleaf template named "detailHero.html" will be used to render the response for the request.
     */
    @GetMapping("detailVolunteer")
    public String detailVolunteer(Integer id, Model model) {
//        calls the method from Service (pass through method from DAO) that retrieves the Volunteer object with the selected id and assigns it to Volunteer variable
        Volunteer volunteer = volunteerService.getVolunteerByID(id);
//        retrieve a list of nonprofit objects to which the volunteer belongs. The method is passed the volunteer object, volunteer, from above
        List<Nonprofit> getVolunteerByNonprofit = nonprofitService.getNonprofitsByVolunteer(volunteer);


//        adds the Volunteer object to the Model with the attribute name "volunteer". This will make the Volunteer object accessible in the Thymeleaf template.
        model.addAttribute("volunteer", volunteer);
//       adds the list of Nonprofit objects to the Model with the attribute name "nonprofits". This will make the list of Nonprofits accessible in the Thymeleaf template.
        model.addAttribute("nonprofits", getVolunteerByNonprofit);

        return "detailVolunteer";
    }
}
