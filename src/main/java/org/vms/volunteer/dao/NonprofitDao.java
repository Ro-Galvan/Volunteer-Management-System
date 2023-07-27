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


//    needed for the many-to-many relationship that Nonprofit is managing
//    TODO more so needed for testing at least it was only used in hero like that
//    List<Nonprofit> getNonprofitsByVolunteer(Volunteer volunteer);

//    extra method to show the nonprofits by assignments ON THE Assignment DETAILS PAGE:
//    List<Nonprofit> getNonprofitsByAssignment(Assignment assignment);
}
