package org.vms.volunteer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.vms.volunteer.dto.Nonprofit;
import org.vms.volunteer.dto.Skill;
import org.vms.volunteer.dto.Volunteer;
import org.vms.volunteer.service.NonprofitService;
import org.vms.volunteer.service.VolunteerService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class NonprofitController {
    @Autowired
    NonprofitService nonprofitService;

    @Autowired
    VolunteerService volunteerService;

    @GetMapping("nonprofits")
    public String displayNonprofits(Model model) {
        List<Nonprofit> nonprofits = nonprofitService.getAllNonprofits();
        List<Volunteer> volunteers = volunteerService.getAllVolunteers();

        model.addAttribute("volunteers", volunteers);
        model.addAttribute("nonprofits", nonprofits);
        return "nonprofits";
    }


//              **************ADD Nonprofit*************
    @PostMapping("addNonprofit")
    public String addNonprofit(HttpServletRequest request, Model model) {
//        use the getParameterValues method to get a string array of volunteerIDs
        String[] volunteerIDs = request.getParameterValues("volunteerID");

//        take in a Nonprofit object that captures the properties/fields, use HttpServletRequest object to capture those fields
        Nonprofit nonprofit = new Nonprofit();
        nonprofit.setCompanyName(request.getParameter("companyName"));
        nonprofit.setPhoneNumber(request.getParameter("phoneNumber"));
        nonprofit.setEmail(request.getParameter("email"));
        nonprofit.setAddress(request.getParameter("address"));
        nonprofit.setMission(request.getParameter("mission"));

        model.addAttribute("nonprofit", nonprofit);

//         create an empty list of volunteers, loop through the volunteerIDs, retrieve each volunteer, and add it to the list.
        List<Volunteer> volunteerArrayList = new ArrayList<>();
        for(String volunteerID : volunteerIDs) {
            volunteerArrayList.add(volunteerService.getVolunteerByID(Integer.parseInt(volunteerID)));
        }
        nonprofit.setVolunteers(volunteerArrayList);

        nonprofitService.addNonprofit(nonprofit);

        return "redirect:/nonprofits";

    }


}
