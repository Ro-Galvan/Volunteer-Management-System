package org.vms.volunteer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.vms.volunteer.dto.Nonprofit;
import org.vms.volunteer.service.NonprofitService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class NonprofitController {
    @Autowired
    NonprofitService nonprofitService;


    @GetMapping("nonprofits")
    public String displayNonprofits(Model model) {
        List<Nonprofit> nonprofits = nonprofitService.getAllNonprofits();

        model.addAttribute("nonprofits", nonprofits);
        return "nonprofits";
    }

//              **************ADD Nonprofit*************
    @PostMapping("addNonprofit")
    public String addNonprofit(HttpServletRequest request) {
//        todo FYI don't need to do the below, the 2nd part does the same thing
//        String companyName = request.getParameter("companyName");
//        String phoneNumber = request.getParameter("phoneNumber");
//        String email = request.getParameter("email");
//        String address = request.getParameter("address");
//        String mission = request.getParameter("mission");

//        take in a Nonprofit object that captures the properties/fields, use HttpServletRequest object to capture those fields
        Nonprofit nonprofit = new Nonprofit();
        nonprofit.setCompanyName(request.getParameter("companyName"));
        nonprofit.setPhoneNumber(request.getParameter("phoneNumber"));
        nonprofit.setEmail(request.getParameter("email"));
        nonprofit.setAddress(request.getParameter("address"));
        nonprofit.setMission(request.getParameter("mission"));

        nonprofitService.addNonprofit(nonprofit);

        return "redirect:/nonprofits";
    }

}
