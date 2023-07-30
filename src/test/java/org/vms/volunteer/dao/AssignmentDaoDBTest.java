package org.vms.volunteer.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.vms.volunteer.dto.Assignment;
import org.vms.volunteer.dto.Nonprofit;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AssignmentDaoDBTest {
    @Autowired
    AssignmentDao assignmentDao;

    @Autowired
    private NonprofitDao nonprofitDao;
// adding them so all the methods have access to them and I don't have to keep repeating code
    private Assignment testAssignment;
    private Nonprofit testNonprofit;

    @BeforeEach
    void setUp() {
        // Clear data from the Assignment and Nonprofit tables before each test
// Delete all assignments
        List<Assignment> assignments = assignmentDao.getAllAssignments();
//        enhanced for loop" or "foreach loop" that is  iterating over elements in a collection or an array. The arrow (->) used in this context is part of Java's lambda expression syntax, which is used to define a lambda or an anonymous function.
        assignments.forEach(assignment -> assignmentDao.deleteAssignmentByID(assignment.getId()));

        // Delete all nonprofits
        List<Nonprofit> nonprofits = nonprofitDao.getAllNonprofits();
        nonprofits.forEach(nonprofit -> nonprofitDao.deleteNonprofitByID(nonprofit.getId()));

        // Create a test Nonprofit
        testNonprofit = new Nonprofit("Tech4Good", "757-555-1234", "test@test.com", "321 Pine Blvd, Raleigh, NC", "Using technology for social impact");
        testNonprofit = nonprofitDao.addNonprofit(testNonprofit);

        // Create a test Assignment
        testAssignment = new Assignment(1, "Database Management", "Optimizing queries", LocalDate.now(), testNonprofit);
        testAssignment = assignmentDao.addAssignment(testAssignment);

        
    }

//    The below test is arranging the info instead of creating a test nonprofit & assignment in the @BeforeEach to avoid DRY
//    leaving as FYI to have an example of how else it can be done
    @Test
    void addAssignment() {
        // Arrange -- using constructors from my DTO
        Nonprofit nonprofit = new Nonprofit("Tech4Good", "757-555-1234", "test@test.com", "321 Pine Blvd, Raleigh, NC", "Using technology for social impact");
        nonprofit = nonprofitDao.addNonprofit(nonprofit);

        Assignment assignment = new Assignment(1, "Database Management", "Optimizing queries", LocalDate.now(), nonprofit);

        // Act
        Assignment addedAssignment = assignmentDao.addAssignment(assignment);

        // Assert
        assertNotNull(addedAssignment);
        assertNotNull(addedAssignment.getId());
        assertEquals("Database Management", addedAssignment.getTitle());
        assertEquals("Optimizing queries", addedAssignment.getAdditionalInfo());
        assertEquals(LocalDate.now(), addedAssignment.getDate());
        assertNotNull(addedAssignment.getNonprofit());
        assertEquals(nonprofit.getCompanyName(), addedAssignment.getNonprofit().getCompanyName());

    }

    //    The below test moves the arrange portion to the @BeforeEach to avoid DRY. Before each test, a test nonprofit & assignment is created to use withing the method
    @Test
    void getAssignmentByID() {
        // Act
        Assignment retrievedAssignment = assignmentDao.getAssignmentByID(testAssignment.getId());

        // Assert
        assertNotNull(retrievedAssignment);
        assertEquals(testAssignment.getId(), retrievedAssignment.getId());
        assertEquals("Database Management", retrievedAssignment.getTitle());
        assertEquals("Optimizing queries", retrievedAssignment.getAdditionalInfo());
        assertEquals(LocalDate.now(), retrievedAssignment.getDate());
        assertNotNull(retrievedAssignment.getNonprofit());
        assertEquals(testNonprofit.getCompanyName(), retrievedAssignment.getNonprofit().getCompanyName());
    }

    @Test
    void getAllAssignments() {
    }

    @Test
    void updateAssignment() {
    }

    @Test
    void deleteAssignmentByID() {
    }
}