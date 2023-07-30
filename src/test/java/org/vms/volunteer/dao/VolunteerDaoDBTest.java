package org.vms.volunteer.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.vms.volunteer.dto.Nonprofit;
import org.vms.volunteer.dto.Volunteer;

import java.util.Collections;
import java.util.List;

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
    @Transactional
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
    @Transactional
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
    @Transactional
    void getAllVolunteers() {
        // Arrange
        // Create test Nonprofit objects and add them to the database
        Nonprofit nonprofit1 = new Nonprofit(
                "Test Nonprofit 1",
                "111-111-1111",
                "nonprofit1@example.com",
                "123 Test St, Test City",
                "Test Nonprofit 1 Mission"
        );
        Nonprofit nonprofit2 = new Nonprofit(
                "Test Nonprofit 2",
                "222-222-2222",
                "nonprofit2@example.com",
                "456 Test St, Test City",
                "Test Nonprofit 2 Mission"
        );
        nonprofitDao.addNonprofit(nonprofit1);
        nonprofitDao.addNonprofit(nonprofit2);

        // Create test Volunteer objects and add them to the database
        Volunteer volunteer1 = new Volunteer("John", "Doe", "1111111111", "john.doe@example.com", "New York", "NY");
        volunteer1.setNonprofits(Collections.singletonList(nonprofit1));
        volunteerDao.addVolunteer(volunteer1);

        Volunteer volunteer2 = new Volunteer("Jane", "Smith", "2222222222", "jane.smith@example.com", "Los Angeles", "CA");
        volunteer2.setNonprofits(Collections.singletonList(nonprofit2));
        volunteerDao.addVolunteer(volunteer2);

        // Act
        List<Volunteer> allVolunteers = volunteerDao.getAllVolunteers();

        // Assert
        assertNotNull(allVolunteers); // Verify that the returned list is not null
        assertEquals(2, allVolunteers.size()); // Verify that the returned list contains exactly 2 volunteers

        // Verify that the volunteers in the list match the data we added
        assertVolunteerEquals(volunteer1, allVolunteers.get(0));
        assertVolunteerEquals(volunteer2, allVolunteers.get(1));
    }

    // Helper method to assert that two Volunteer objects are equal based on their properties
    private void assertVolunteerEquals(Volunteer expected, Volunteer actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getPhoneNumber(), actual.getPhoneNumber());
        assertEquals(expected.getEmail(), actual.getEmail());
        assertEquals(expected.getCity(), actual.getCity());
        assertEquals(expected.getState(), actual.getState());
        // Add more assertions based on your requirements
    }

    @Test
    @Transactional
    void updateVolunteer() {
        // Arrange
        // Create a test Nonprofit object and add it to the database
        Nonprofit testNonprofit = new Nonprofit(
                "Test Nonprofit",
                "111-111-1111",
                "nonprofit@example.com",
                "123 Test St, Test City",
                "Test Nonprofit Mission"
        );
        nonprofitDao.addNonprofit(testNonprofit);

        // Create a test Volunteer object and add it to the database
        Volunteer testVolunteer = new Volunteer("John", "Doe", "1111111111", "john.doe@example.com", "New York", "NY");
        testVolunteer.setNonprofits(Collections.singletonList(testNonprofit));
        volunteerDao.addVolunteer(testVolunteer);

        // Update the test Volunteer's information
        testVolunteer.setFirstName("Updated John");
        testVolunteer.setLastName("Updated Doe");
        testVolunteer.setPhoneNumber("2222222222");
        testVolunteer.setEmail("updated.john.doe@example.com");
        testVolunteer.setCity("Los Angeles");
        testVolunteer.setState("CA");

        // Act
        volunteerDao.updateVolunteer(testVolunteer);

        // Retrieve the updated Volunteer from the database
        Volunteer updatedVolunteer = volunteerDao.getVolunteerByID(testVolunteer.getId());

        // Assert
        assertNotNull(updatedVolunteer); // Verify that the retrieved volunteer is not null

        // Verify that the volunteer's information was updated correctly
        assertEquals(testVolunteer.getId(), updatedVolunteer.getId());
        assertEquals(testVolunteer.getFirstName(), updatedVolunteer.getFirstName());
        assertEquals(testVolunteer.getLastName(), updatedVolunteer.getLastName());
        assertEquals(testVolunteer.getPhoneNumber(), updatedVolunteer.getPhoneNumber());
        assertEquals(testVolunteer.getEmail(), updatedVolunteer.getEmail());
        assertEquals(testVolunteer.getCity(), updatedVolunteer.getCity());
        assertEquals(testVolunteer.getState(), updatedVolunteer.getState());
        // Add more assertions based on your requirements
    }

    @Test
    @Transactional
    void deleteVolunteerByID() {
        // Arrange
        // Create a test Nonprofit object and add it to the database
        Nonprofit testNonprofit = new Nonprofit(
                "Test Nonprofit",
                "111-111-1111",
                "nonprofit@example.com",
                "123 Test St, Test City",
                "Test Nonprofit Mission"
        );
        nonprofitDao.addNonprofit(testNonprofit);

        // Create a test Volunteer object and add it to the database
        Volunteer testVolunteer = new Volunteer("John", "Doe", "1111111111", "john.doe@example.com", "New York", "NY");
        testVolunteer.setNonprofits(Collections.singletonList(testNonprofit));
        testVolunteer = volunteerDao.addVolunteer(testVolunteer);

        int volunteerId = testVolunteer.getId();

        // Act
        volunteerDao.deleteVolunteerByID(volunteerId);

        // Try to retrieve the deleted Volunteer from the database
        Volunteer deletedVolunteer = volunteerDao.getVolunteerByID(volunteerId);

        // Assert
        assertNull(deletedVolunteer); // Verify that the retrieved volunteer is null after deletion
    }
}