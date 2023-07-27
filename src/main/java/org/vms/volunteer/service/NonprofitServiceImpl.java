package org.vms.volunteer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vms.volunteer.dao.NonprofitDao;
import org.vms.volunteer.dto.Nonprofit;

import java.util.List;
@Service
public class NonprofitServiceImpl implements NonprofitService{
    @Autowired
    NonprofitDao nonprofitDao;

    @Override
    public Nonprofit addNonprofit(Nonprofit nonprofit) {
        return nonprofitDao.addNonprofit(nonprofit);
    }

    @Override
    public Nonprofit getNonprofitByID(int id) {
        return nonprofitDao.getNonprofitByID(id);
    }

    @Override
    public List<Nonprofit> getAllNonprofits() {
        return nonprofitDao.getAllNonprofits();
    }

    @Override
    public void updateNonprofit(Nonprofit nonprofit) {
        nonprofitDao.updateNonprofit(nonprofit);

    }

    @Override
    public void deleteNonprofitByID(int id) {
        nonprofitDao.deleteNonprofitByID(id);
    }
}
