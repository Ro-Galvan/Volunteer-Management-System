package org.vms.volunteer.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.vms.volunteer.dto.Nonprofit;
import org.vms.volunteer.dto.Volunteer;
import org.vms.volunteer.mapper.NonprofitMapper;

import java.util.List;
@Repository
public class NonprofitDaoDB implements NonprofitDao{
    @Autowired
    JdbcTemplate jdbc;

    @Override
    @Transactional
    public Nonprofit addNonprofit(Nonprofit nonprofit) {
        final String SQL = "INSERT INTO nonprofit(companyName, phoneNum, email, address, mission) VALUES(?,?,?,?,?)";
        jdbc.update(SQL,
                nonprofit.getCompanyName(),
                nonprofit.getPhoneNumber(),
                nonprofit.getEmail(),
                nonprofit.getAddress(),
                nonprofit.getMission());

        // Retrieve the last inserted ID
        String selectLastIdQuery = "SELECT LAST_INSERT_ID()";
        int newId = jdbc.queryForObject(selectLastIdQuery, Integer.class);
        nonprofit.setId(newId);

        return nonprofit;
    }


    @Override
    public Nonprofit getNonprofitByID(int id) {
        try {
            final String SQL = "SELECT * FROM nonprofit WHERE nonprofitID = ?";
            return jdbc.queryForObject(SQL, new NonprofitMapper(), id);
        } catch(DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Nonprofit> getAllNonprofits() {
        final String SQL = "SELECT * FROM nonprofit";
        return jdbc.query(SQL, new NonprofitMapper());
    }

    @Override
    public void updateNonprofit(Nonprofit nonprofit) {
        final String SQL = "UPDATE nonprofit SET companyName = ?, phoneNum = ?, email = ?, address = ?, mission = ? WHERE nonprofitID = ?";
        jdbc.update(SQL,
                nonprofit.getCompanyName(),
                nonprofit.getPhoneNumber(),
                nonprofit.getEmail(),
                nonprofit.getAddress(),
                nonprofit.getMission(),
                nonprofit.getId());
    }

    @Override
    @Transactional
    public void deleteNonprofitByID(int id) {
//        1. delete from bridge table
        final String DELETE_NONPROFIT_VOLUNTEER = "DELETE FROM nonprofit_volunteer WHERE nonprofitID = ?";
        jdbc.update(DELETE_NONPROFIT_VOLUNTEER, id);
//        2. delete from assignment table
        final String DELETE_NONPROFIT_ASSIGNMENT = "DELETE FROM assignment WHERE nonprofitID = ?";
        jdbc.update(DELETE_NONPROFIT_ASSIGNMENT, id);
//        3. delete from nonprofit table
        final String sql = "DELETE FROM nonprofit WHERE nonprofitID = ?";
        jdbc.update(sql, id);

    }

    // this helps with the volunteer details page to display associated nonprofits
    @Override
    public List<Nonprofit> getNonprofitsByVolunteer(Volunteer volunteer) {
        final String sql = "SELECT n.*"
                +"FROM `nonprofit` n "
                +"INNER JOIN nonprofit_volunteer nv ON n.nonprofitID = nv.nonprofitID "
                +"WHERE nv.volunteerID  = ?";

        List<Nonprofit> nonprofits = jdbc.query(sql, new NonprofitMapper(), volunteer.getId());

        return nonprofits;
    }


//    TODO I believe this method is needed for testing later not for frontend
//    @Override
//    public List<Nonprofit> getNonprofitsByVolunteer(Volunteer volunteer) {
//        return null;
//    }

//    @Override
//    public List<Nonprofit> getNonprofitsByAssignment(Assignment assignment) {
//        return null;
//    }
}