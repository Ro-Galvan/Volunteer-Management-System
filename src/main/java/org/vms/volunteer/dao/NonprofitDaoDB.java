package org.vms.volunteer.dao;

import org.vms.volunteer.dto.Assignment;
import org.vms.volunteer.dto.Nonprofit;
import org.vms.volunteer.dto.Volunteer;

import java.util.List;

public class NonprofitDaoDB implements NonprofitDao{
    @Override
    public Nonprofit addNonprofit(Nonprofit nonprofit) {
        return null;
    }

    @Override
    public Nonprofit getNonprofitByID(int id) {
        return null;
    }

    @Override
    public List<Nonprofit> getAllNonprofits() {
        return null;
    }

    @Override
    public void updateNonprofit(Nonprofit nonprofit) {

    }

    @Override
    public void deleteNonprofitByID(int id) {

    }

    @Override
    public List<Nonprofit> getNonprofitsByVolunteer(Volunteer volunteer) {
        return null;
    }

    @Override
    public List<Nonprofit> getNonprofitsByAssignment(Assignment assignment) {
        return null;
    }
}
