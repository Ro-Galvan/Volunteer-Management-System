package org.vms.volunteer.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.vms.volunteer.dto.Nonprofit;
import org.vms.volunteer.dto.Volunteer;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class VolunteerDaoDBTest {
    @Autowired
    private VolunteerDaoDB volunteerDao;

    @Autowired
    private NonprofitDaoDB nonprofitDao;


    @BeforeEach
    void setUp() {
        // You can use this method to set up the test environment
        // For example, clearing the database, adding test data, etc.
    }

    @Test
    void addVolunteer() {
        // Arrange
        // Create a test Nonprofit object with the required attributes for the Volunteer
        Nonprofit testNonprofit = new Nonprofit(
                "Test Nonprofit Company",
                "123-456-7890",
                "test@example.com",
                "123 Test St, Test City",
                "Test Nonprofit Mission"
        );
        nonprofitDao.addNonprofit(testNonprofit); // Add the test Nonprofit to the database to get its ID

        Volunteer testVolunteer = new Volunteer("John", "Doe", "1234567890", "john.doe@example.com", "New York", "NY");
        testVolunteer.setNonprofits(Collections.singletonList(testNonprofit)); // Set the test Nonprofit to the volunteer

        // Act
        Volunteer addedVolunteer = volunteerDao.addVolunteer(testVolunteer);

        // Assert
        assertNotNull(addedVolunteer); // Verify that the returned volunteer is not null
        assertEquals(testVolunteer.getFirstName(), addedVolunteer.getFirstName());
        assertEquals(testVolunteer.getLastName(), addedVolunteer.getLastName());
        // Add more assertions based on your requirements
    }


    @Test
    void getVolunteerByID() {
        // Arrange
        // Create a test Nonprofit object and add it to the database
        Nonprofit testNonprofit = new Nonprofit(
                "Test Nonprofit Company",
                "123-456-7890",
                "test@example.com",
                "123 Test St, Test City",
                "Test Nonprofit Mission"
        );
        nonprofitDao.addNonprofit(testNonprofit); // Add the test Nonprofit to the database to get its ID

        // Create a test Volunteer object and add it to the database
        Volunteer testVolunteer = new Volunteer("John", "Doe", "1234567890", "john.doe@example.com", "New York", "NY");
        testVolunteer.setNonprofits(Collections.singletonList(testNonprofit)); // Set the test Nonprofit to the volunteer
        volunteerDao.addVolunteer(testVolunteer); // Add the test Volunteer to the database to get its ID

        // Act
        Volunteer retrievedVolunteer = volunteerDao.getVolunteerByID(testVolunteer.getId());

        // Assert
        assertNotNull(retrievedVolunteer); // Verify that the retrieved volunteer is not null
        assertEquals(testVolunteer.getId(), retrievedVolunteer.getId());
        assertEquals(testVolunteer.getFirstName(), retrievedVolunteer.getFirstName());
        assertEquals(testVolunteer.getLastName(), retrievedVolunteer.getLastName());
        assertEquals(testVolunteer.getPhoneNumber(), retrievedVolunteer.getPhoneNumber());
        assertEquals(testVolunteer.getEmail(), retrievedVolunteer.getEmail());
        assertEquals(testVolunteer.getCity(), retrievedVolunteer.getCity());
        assertEquals(testVolunteer.getState(), retrievedVolunteer.getState());

        // Add more assertions to test other attributes and ensure data integrity.
    }

    @Test
    void getAllVolunteers() {
    }

    @Test
    void updateVolunteer() {
    }

    @Test
    void deleteVolunteerByID() {
    }
}