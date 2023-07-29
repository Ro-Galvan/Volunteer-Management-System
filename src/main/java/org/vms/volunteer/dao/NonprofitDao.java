package org.vms.volunteer.dao;


import org.vms.volunteer.dto.Assignment;
import org.vms.volunteer.dto.Nonprofit;
import org.vms.volunteer.dto.Volunteer;

import java.util.List;

public interface NonprofitDao {
    Nonprofit addNonprofit(Nonprofit nonprofit);
    Nonprofit getNonprofitByID(int id);
    List<Nonprofit> getAllNonprofits();
    void updateNonprofit(Nonprofit nonprofit);
    void deleteNonprofitByID(int id);


//    extra method to show the nonprofits by volunteer ON THE volunteer DETAILS PAGE:
    List<Nonprofit> getNonprofitsByVolunteer(Volunteer volunteer);
}
