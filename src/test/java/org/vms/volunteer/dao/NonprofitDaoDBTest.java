package org.vms.volunteer.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.vms.volunteer.dto.Nonprofit;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class NonprofitDaoDBTest {
    @Autowired
    NonprofitDao nonprofitDao;

    @BeforeEach
    void setUp() {
    // Delete all nonprofits
        List<Nonprofit> nonprofits = nonprofitDao.getAllNonprofits();
        nonprofits.forEach(nonprofit -> nonprofitDao.deleteNonprofitByID(nonprofit.getId()));
    }

    @Test
    @Transactional
    void addNonprofit() {
        // Arrange - using constructors from my DTO
        Nonprofit nonprofit = new Nonprofit("Tech4Good", "757-555-1234", "test@test.com", "321 Pine Blvd, Raleigh, NC", "Using technology for social impact");

        // Act - Call the addNonprofit() method
        nonprofit = nonprofitDao.addNonprofit(nonprofit);

        // Assert
        assertNotNull(nonprofit);
        assertNotNull(nonprofit.getId());
        assertEquals("Tech4Good", nonprofit.getCompanyName());
        assertEquals("757-555-1234", nonprofit.getPhoneNumber());
        assertEquals("test@test.com", nonprofit.getEmail());
        assertEquals("321 Pine Blvd, Raleigh, NC", nonprofit.getAddress());
        assertEquals("Using technology for social impact", nonprofit.getMission());
    }

    @Test
    @Transactional
    void getNonprofitByID() {
        // Arrange - add a nonprofit to the database
        Nonprofit nonprofit = new Nonprofit("Tech4Good", "757-555-1234", "test@test.com", "321 Pine Blvd, Raleigh, NC", "Using technology for social impact");
        nonprofit = nonprofitDao.addNonprofit(nonprofit);

        // Act - Call the getNonprofitByID() method
        Nonprofit retrievedNonprofit = nonprofitDao.getNonprofitByID(nonprofit.getId());

        // Assert
        assertNotNull(retrievedNonprofit);
        assertEquals(nonprofit.getId(), retrievedNonprofit.getId());
        assertEquals("Tech4Good", retrievedNonprofit.getCompanyName());
        assertEquals("757-555-1234", retrievedNonprofit.getPhoneNumber());
        assertEquals("test@test.com", retrievedNonprofit.getEmail());
        assertEquals("321 Pine Blvd, Raleigh, NC", retrievedNonprofit.getAddress());
        assertEquals("Using technology for social impact", retrievedNonprofit.getMission());
    }

    @Test
    @Transactional
    void getAllNonprofits() {
        // Arrange - Add two nonprofits to the database
        Nonprofit nonprofit1 = new Nonprofit("Tech4Good", "757-555-1234", "test1@test.com", "321 Pine Blvd, Raleigh, NC", "Using technology for social impact");
        Nonprofit nonprofit2 = new Nonprofit("Charity Aid", "919-555-5678", "test2@test.com", "123 Oak St, Raleigh, NC", "Providing aid to those in need");

        nonprofit1 = nonprofitDao.addNonprofit(nonprofit1);
        nonprofit2 = nonprofitDao.addNonprofit(nonprofit2);

        // Act - Call the getAllNonprofits() method
        List<Nonprofit> allNonprofits = nonprofitDao.getAllNonprofits();

        // Assert
        assertNotNull(allNonprofits);
        assertEquals(2, allNonprofits.size()); // Assuming there are two test nonprofits in the database
        assertEquals("Tech4Good", allNonprofits.get(0).getCompanyName());
        assertEquals("Charity Aid", allNonprofits.get(1).getCompanyName());
    }


    @Test
    @Transactional
    void updateNonprofit() {
        // Arrange - add a nonprofit to the database
        Nonprofit nonprofit = new Nonprofit("Tech4Good", "757-555-1234", "test@test.com", "321 Pine Blvd, Raleigh, NC", "Using technology for social impact");
        nonprofit = nonprofitDao.addNonprofit(nonprofit);

        // Modify the nonprofit object
        nonprofit.setCompanyName("Updated Company Name");
        nonprofit.setPhoneNumber("555-555-5555");
        nonprofit.setEmail("updated@test.com");
        nonprofit.setAddress("123 Oak St, Raleigh, NC");
        nonprofit.setMission("Updated mission statement");

        // Act - Call the updateNonprofit() method
        nonprofitDao.updateNonprofit(nonprofit);

        // Assert - Get the updated nonprofit by ID and verify the changes
        Nonprofit updatedNonprofit = nonprofitDao.getNonprofitByID(nonprofit.getId());
        assertNotNull(updatedNonprofit);
        assertEquals("Updated Company Name", updatedNonprofit.getCompanyName());
        assertEquals("555-555-5555", updatedNonprofit.getPhoneNumber());
        assertEquals("updated@test.com", updatedNonprofit.getEmail());
        assertEquals("123 Oak St, Raleigh, NC", updatedNonprofit.getAddress());
        assertEquals("Updated mission statement", updatedNonprofit.getMission());
    }

    @Test
    @Transactional
    void deleteNonprofitByID() {
        // Arrange - add a nonprofit to the database
        Nonprofit nonprofit = new Nonprofit("Tech4Good", "757-555-1234", "test@test.com", "321 Pine Blvd, Raleigh, NC", "Using technology for social impact");
        nonprofit = nonprofitDao.addNonprofit(nonprofit);

        // Act - Call the deleteNonprofitByID() method
        nonprofitDao.deleteNonprofitByID(nonprofit.getId());

        // Assert - Try to get the deleted nonprofit by ID, it should return null
        Nonprofit deletedNonprofit = nonprofitDao.getNonprofitByID(nonprofit.getId());
        assertNull(deletedNonprofit);
    }

}