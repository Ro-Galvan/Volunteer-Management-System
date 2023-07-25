package org.vms.volunteer.dao;


import org.vms.volunteer.dto.Nonprofit;

import java.util.List;

public interface NonprofitDao {
    Nonprofit addNonprofit(Nonprofit nonprofit);
    Nonprofit getNonprofitByID(int id);
    List<Nonprofit> getAllNonprofits();
    void updateNonprofit(Nonprofit nonprofit);
    void deleteNonprofitByID(int id);
}
