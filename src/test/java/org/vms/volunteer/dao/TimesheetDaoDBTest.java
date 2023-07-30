package org.vms.volunteer.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.vms.volunteer.dto.Assignment;
import org.vms.volunteer.dto.Nonprofit;
import org.vms.volunteer.dto.Timesheet;
import org.vms.volunteer.dto.Volunteer;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class TimesheetDaoDBTest {
    @Autowired
    TimesheetDao timesheetDao;
    @Autowired
    VolunteerDao volunteerDao;
    @Autowired
    AssignmentDao assignmentDao;
    @Autowired
    NonprofitDao nonprofitDao;
//so other test methods can use the objects in the setup
    private Nonprofit testNonprofit;
    private Volunteer testVolunteer;
    private Assignment testAssignment;

    @BeforeEach
    void setUp() {
        // Clear all Timesheets
        timesheetDao.getAllTimesheets().forEach(timesheet -> timesheetDao.deleteTimesheetByID(timesheet.getId()));

        // Clear all Assignments
        assignmentDao.getAllAssignments().forEach(assignment -> assignmentDao.deleteAssignmentByID(assignment.getId()));

        // Clear all Nonprofits
        nonprofitDao.getAllNonprofits().forEach(nonprofit -> nonprofitDao.deleteNonprofitByID(nonprofit.getId()));

        // Create a test Nonprofit
        testNonprofit = new Nonprofit("Test Nonprofit Company", "123-456-7890",
                "test@example.com", "123 Test St, Test City", "Test Nonprofit Mission");
        nonprofitDao.addNonprofit(testNonprofit);

        // Create a test Volunteer
        testVolunteer = new Volunteer("John", "Doe", "1234567890", "john.doe@example.com",
                "New York", "NY");
        testVolunteer.setNonprofits(Collections.singletonList(testNonprofit));
        volunteerDao.addVolunteer(testVolunteer);

        // Create a test Assignment
        testAssignment = new Assignment("Test Assignment", "Test Assignment Info",
                LocalDate.of(2023, 7, 29), testNonprofit);
        assignmentDao.addAssignment(testAssignment);

    }

    @Test
    @Transactional
    void addTimesheet() {
        // Create a test Timesheet
        Timesheet testTimesheet = new Timesheet("8", LocalDate.of(2023, 7, 29),
                testVolunteer, testAssignment);

        // Act
        Timesheet addedTimesheet = timesheetDao.addTimesheet(testTimesheet);

        // Assert
        assertNotNull(addedTimesheet);
        assertEquals(testTimesheet.getHoursLogged(), addedTimesheet.getHoursLogged());
        assertEquals(testTimesheet.getDate(), addedTimesheet.getDate());
        assertEquals(testTimesheet.getVolunteer(), addedTimesheet.getVolunteer());
        assertEquals(testTimesheet.getAssignment(), addedTimesheet.getAssignment());
    }

    @Test
    @Transactional
    void getTimesheetByID() {
        // Create a test Timesheet
        Timesheet testTimesheet = new Timesheet("8", LocalDate.of(2023, 7, 29),
                testVolunteer, testAssignment);

        // Add the test Timesheet to the database
        Timesheet addedTimesheet = timesheetDao.addTimesheet(testTimesheet);

        // Get the Timesheet by its ID
        Timesheet retrievedTimesheet = timesheetDao.getTimesheetByID(addedTimesheet.getId());

        // Asserts
        assertNotNull(retrievedTimesheet); // Verify that the retrieved Timesheet is not null

        // Print the retrieved Timesheet for debugging
        System.out.println("Retrieved Timesheet: " + retrievedTimesheet);

        // Verify the properties of the retrieved Timesheet match the properties of the test Timesheet
        assertEquals(testTimesheet.getHoursLogged(), retrievedTimesheet.getHoursLogged());
        assertEquals(testTimesheet.getDate(), retrievedTimesheet.getDate());

        // Use testVolunteer.getId() and testAssignment.getId() to compare IDs
        assertEquals(testVolunteer.getId(), retrievedTimesheet.getVolunteer().getId());
        assertEquals(testAssignment.getId(), retrievedTimesheet.getAssignment().getId());
    }


    @Test
    @Transactional
    void getAllTimesheets() {
        // Arrange
        // Create test Timesheets and add them to the database
        Timesheet testTimesheet1 = new Timesheet("8", LocalDate.parse("2023-07-29"), testVolunteer, testAssignment);
        Timesheet testTimesheet2 = new Timesheet("4", LocalDate.parse("2023-07-30"), testVolunteer, testAssignment);

        timesheetDao.addTimesheet(testTimesheet1);
        timesheetDao.addTimesheet(testTimesheet2);

        // Act
        List<Timesheet> allTimesheets = timesheetDao.getAllTimesheets();

        // Assert
        assertNotNull(allTimesheets); // Verify that the retrieved list is not null
        assertEquals(2, allTimesheets.size()); // Verify that the list contains 2 Timesheets

        // Verify the properties of the retrieved Timesheets match the properties of the test Timesheets
        Timesheet retrievedTimesheet1 = allTimesheets.get(0);
        assertEquals(testTimesheet1.getHoursLogged(), retrievedTimesheet1.getHoursLogged());
        assertEquals(testTimesheet1.getDate(), retrievedTimesheet1.getDate());
        assertEquals(testVolunteer.getId(), retrievedTimesheet1.getVolunteer().getId()); // Use testVolunteer.getId()
        assertEquals(testAssignment.getId(), retrievedTimesheet1.getAssignment().getId()); // Use testAssignment.getId()

        Timesheet retrievedTimesheet2 = allTimesheets.get(1);
        assertEquals(testTimesheet2.getHoursLogged(), retrievedTimesheet2.getHoursLogged());
        assertEquals(testTimesheet2.getDate(), retrievedTimesheet2.getDate());
        assertEquals(testVolunteer.getId(), retrievedTimesheet2.getVolunteer().getId()); // Use testVolunteer.getId()
        assertEquals(testAssignment.getId(), retrievedTimesheet2.getAssignment().getId()); // Use testAssignment.getId()
    }



    @Test
    @Transactional
    void updateTimesheet() {
        // Arrange
        // Create a test Timesheet and add it to the database
        Timesheet testTimesheet = new Timesheet("8", LocalDate.parse("2023-07-29"), testVolunteer, testAssignment);
        timesheetDao.addTimesheet(testTimesheet);

        // Modify some properties of the test Timesheet
        testTimesheet.setHoursLogged("6");
        testTimesheet.setDate(LocalDate.parse("2023-07-30"));

        // Retrieve the Volunteer and Assignment objects from the database
        Volunteer updatedVolunteer = volunteerDao.getVolunteerByID(testVolunteer.getId());
        Assignment updatedAssignment = assignmentDao.getAssignmentByID(testAssignment.getId());

        // Update the volunteer and assignment fields of the testTimesheet
        testTimesheet.setVolunteer(updatedVolunteer);
        testTimesheet.setAssignment(updatedAssignment);

        // Act
        timesheetDao.updateTimesheet(testTimesheet);

        // Retrieve the updated Timesheet from the database
        Timesheet updatedTimesheet = timesheetDao.getTimesheetByID(testTimesheet.getId());

        // Assert
        assertNotNull(updatedTimesheet); // Verify that the retrieved Timesheet is not null
        assertEquals(testTimesheet.getId(), updatedTimesheet.getId());
        assertEquals(testTimesheet.getHoursLogged(), updatedTimesheet.getHoursLogged());
        assertEquals(testTimesheet.getDate(), updatedTimesheet.getDate());
        assertEquals(testTimesheet.getVolunteer().getId(), updatedTimesheet.getVolunteer().getId());
        assertEquals(testTimesheet.getAssignment().getId(), updatedTimesheet.getAssignment().getId());
    }


    @Test
    @Transactional
    void deleteVolunteerByID() {
        // Arrange
        // Create a test Volunteer and add it to the database
        Volunteer testVolunteer = new Volunteer("John", "Doe", "1234567890", "john.doe@example.com", "New York", "NY");
        testVolunteer.setNonprofits(Collections.singletonList(testNonprofit)); // Set the test Nonprofit to the volunteer
        volunteerDao.addVolunteer(testVolunteer);

        // Act
        volunteerDao.deleteVolunteerByID(testVolunteer.getId());

        // Try to retrieve the deleted Volunteer from the database
        Volunteer deletedVolunteer = volunteerDao.getVolunteerByID(testVolunteer.getId());

        // Assert
        assertNull(deletedVolunteer); // Verify that the deleted Volunteer is not found (should be null)
    }

}