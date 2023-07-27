package org.vms.volunteer.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.vms.volunteer.dto.Assignment;
import org.vms.volunteer.dto.Nonprofit;
import org.vms.volunteer.dto.Volunteer;
import org.vms.volunteer.mapper.NonprofitMapper;
import org.vms.volunteer.mapper.VolunteerMapper;

import java.util.List;
@Repository
public class NonprofitDaoDB implements NonprofitDao{

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Nonprofit addNonprofit(Nonprofit nonprofit) {
        final String SQL = "INSERT INTO nonprofit(companyName, phoneNum, email, address, mission)";
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
//        using helper method for bridge table
        insertIntoNonprofitVolunteerBridgeTable(nonprofit);
        return nonprofit;
    }

    @Override
    public Nonprofit getNonprofitByID(int id) {
        try {
            final String SQL = "SELECT * FROM nonprofit WHERE nonprofitID = ?";
            Nonprofit nonprofit = jdbc.queryForObject(SQL, new NonprofitMapper(), id);
//            helper method returns a List<Volunteer> by ID
            nonprofit.setVolunteers(getVolunteersForNonprofit(id));
            return nonprofit;
        } catch(DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Nonprofit> getAllNonprofits() {
        try {
            final String SQL = "SELECT * FROM nonprofit";
            List<Nonprofit> nonprofitList = jdbc.query(SQL, new NonprofitMapper());

//      looping through nonprofit list and setting the respective volunteer associated with that nonprofit
            for (Nonprofit nonprofit : nonprofitList){
                nonprofit.setVolunteers(getVolunteersForNonprofit(nonprofit.getId()));
            }
            return nonprofitList;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public void updateNonprofit(Nonprofit nonprofit) {
        final String SQL = "INSERT INTO nonprofit(companyName = ?, phoneNum = ?, email = ?, address = ?, mission = ? WHERE nonprofitID = ?)";
        jdbc.update(SQL,
                nonprofit.getCompanyName(),
                nonprofit.getPhoneNumber(),
                nonprofit.getEmail(),
                nonprofit.getAddress(),
                nonprofit.getMission(),
                nonprofit.getId());
//  the old record needs to be deleted from bridge table before updating and adding back to bridge table
        final String DELETE_NONPROFIT_VOLUNTEER = "DELETE FROM nonprofit_volunteer WHERE nonprofitID = ?";
        jdbc.update(DELETE_NONPROFIT_VOLUNTEER, nonprofit.getId());

        insertIntoNonprofitVolunteerBridgeTable(nonprofit);
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

    //    *****PRIVATE HELPER METHODS***
/*
SQL query selects all columns (v.*) from volunteer table, alias v, who are associated with a specific nonprofit (n.nonprofitID = ?)
It achieves this by using two JOIN clauses to connect/match the nonprofit table with the volunteer table
via/ON the nonprofit_volunteer table.
 */
    private List<Volunteer> getVolunteersForNonprofit(int id) {
        final String SQL = "SELECT v.* " +
                "FROM nonprofit n " +
                "JOIN nonprofit_volunteer nv ON n.nonprofitID = nv.nonprofitID " +
                "JOIN volunteer v ON nv.volunteerID = v.volunteerID " +
                "WHERE n.nonprofitID = ?";
        return jdbc.query(SQL, new VolunteerMapper(), id);
    }

    //    This is the bridge table for many-many relationship
    private void insertIntoNonprofitVolunteerBridgeTable(Nonprofit nonprofit){
        final String SQL = "INSERT INTO nonprofit_volunteer (nonprofitID, volunteerID) " +
                "VALUES (?, ?);";
        for(Volunteer volunteer : nonprofit.getVolunteers()) {
            jdbc.update(SQL,
                    nonprofit.getId(),
                    volunteer.getId());
        }
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
