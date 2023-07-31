package org.vms.volunteer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.vms.volunteer.dto.Assignment;
import org.vms.volunteer.dto.Nonprofit;
import org.vms.volunteer.service.NonprofitService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class NonprofitController {
    @Autowired
    NonprofitService nonprofitService;

    //    validation error it found. In this situation, we would send the full set to the page to process like a list, printing out the errors.
//   ******* FYI ******  makes the violations set shared among different requests, leading to unexpected behavior.
    Set<ConstraintViolation<Nonprofit>> violations = new HashSet<>();


    @GetMapping("nonprofits")
    public String displayNonprofits(Model model) {
        List<Nonprofit> nonprofits = nonprofitService.getAllNonprofits();

        model.addAttribute("nonprofits", nonprofits);

        //   added this for validations
        model.addAttribute("errors", violations);
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
//added for validations
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(nonprofit);

        if(violations.isEmpty()) {
            nonprofitService.addNonprofit(nonprofit);
            return "redirect:/nonprofits";
        }
        return "redirect:/nonprofits";

    }

    //              **************EDIT Nonprofit*************
    @GetMapping("editNonprofit")
    public String editNonprofit(Integer id, Model model) {
        Nonprofit nonprofit = nonprofitService.getNonprofitByID(id);
        model.addAttribute("nonprofit", nonprofit);
        //  added this for validations
        model.addAttribute("errors", violations);
        return "editNonprofit";
    }
    @PostMapping("editNonprofit")
    public String performEditNonprofit(Integer id, Nonprofit nonprofit) {
        //   added for validations
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(nonprofit);

        if(violations.isEmpty()) {
            nonprofitService.updateNonprofit(nonprofit);
            return "redirect:/nonprofits";
        }
        return "redirect:/editNonprofit?id="+id;
    }

    //              **************DELETE Nonprofit*************
    @GetMapping("deleteNonprofit")
    public String deleteNonprofit(Integer id) {
        nonprofitService.deleteNonprofitByID(id);
        return "redirect:/nonprofits";
    }
}
