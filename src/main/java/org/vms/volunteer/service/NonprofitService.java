package org.vms.volunteer.service;

import org.vms.volunteer.dto.Nonprofit;
import org.vms.volunteer.dto.Volunteer;

import java.util.List;

public interface NonprofitService {
    Nonprofit addNonprofit(Nonprofit nonprofit);
    Nonprofit getNonprofitByID(int id);
    List<Nonprofit> getAllNonprofits();
    void updateNonprofit(Nonprofit nonprofit);
    void deleteNonprofitByID(int id);
    // this helps with the volunteer details page to display associated nonprofits
    List<Nonprofit> getNonprofitsByVolunteer(Volunteer volunteer);
}
