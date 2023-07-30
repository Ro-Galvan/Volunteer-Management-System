package org.vms.volunteer.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
    void getAllNonprofits() {
    }

    @Test
    void updateNonprofit() {
    }

    @Test
    void deleteNonprofitByID() {
    }
}