package org.vms.volunteer.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
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
    private Assignment assignment1;
    private Assignment assignment2;
    private Nonprofit testNonprofit; //gets passed to the Assignment constructor when creating Assignment Objects

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

        // Nonprofit Object for testing
        testNonprofit = new Nonprofit("Tech4Good", "757-555-1234", "test@test.com", "321 Pine Blvd, Raleigh, NC", "Using technology for social impact");
        testNonprofit = nonprofitDao.addNonprofit(testNonprofit);

        /*
        Since the database generates IDs starting from 1 with auto-increment, the ID should be set to 0 in the test setup
        to indicate that it's an unassigned ID and let the database handle the auto-incrementing when the assignment is added.
         */
        // Assignment Object #1
        assignment1 = new Assignment(0, "Database Management", "Optimizing queries", LocalDate.now(), testNonprofit);
        assignment1 = assignmentDao.addAssignment(assignment1);

        // Assignment Object #2 --- needed for getAllAssignment unit test
        assignment2 = new Assignment(0, "Web Development", "Building responsive websites", LocalDate.now().plusDays(1), testNonprofit);
        assignment2 = assignmentDao.addAssignment(assignment2);
    }

//    The below test has comments that show arranging the info instead of creating a test nonprofit & assignment in the @BeforeEach to avoid DRY
//    leaving as FYI to have an example of how else it can be done
    @Test
    @Transactional
    void addAssignment() {
// Arrange - using the Nonprofit object set up in @BeforeEach & using the Assignment constructor
        Assignment assignment = new Assignment("Marketing Campaign", "Social media promotion", LocalDate.now(), testNonprofit);

        // Act -- Call the addAssignment() method
        assignment = assignmentDao.addAssignment(assignment);

        // Assert
        assertNotNull(assignment);
        assertNotNull(assignment.getId());
        assertEquals("Marketing Campaign", assignment.getTitle());
        assertEquals("Social media promotion", assignment.getAdditionalInfo());
        assertEquals(LocalDate.now(), assignment.getDate());

        // Verify the Nonprofit associated with the Assignment
        assertNotNull(assignment.getNonprofit());
        assertEquals(testNonprofit.getId(), assignment.getNonprofit().getId());
        assertEquals(testNonprofit.getCompanyName(), assignment.getNonprofit().getCompanyName());
    }


    //    The below test moves the arrange portion to the @BeforeEach to avoid DRY. Before each test, a test nonprofit & assignment is created to use withing the method
    @Test
    @Transactional
    void getAssignmentByID() {
        // Act
        Assignment retrievedAssignment = assignmentDao.getAssignmentByID(assignment1.getId());

        // Assert
        assertNotNull(retrievedAssignment);
        assertEquals(assignment1.getId(), retrievedAssignment.getId());
        assertEquals("Database Management", retrievedAssignment.getTitle());
        assertEquals("Optimizing queries", retrievedAssignment.getAdditionalInfo());
        assertEquals(LocalDate.now(), retrievedAssignment.getDate());
        assertNotNull(retrievedAssignment.getNonprofit());
        assertEquals(testNonprofit.getCompanyName(), retrievedAssignment.getNonprofit().getCompanyName());
    }

    @Test
    @Transactional
    void getAllAssignments() {
        // Act
        List<Assignment> allAssignments = assignmentDao.getAllAssignments();

        // Assert
        assertNotNull(allAssignments);
        assertEquals(2, allAssignments.size()); // Assuming there are two test assignments in the database

//      ***** allAssignments.get(0)/(1) is getting the index from the List which starts at index 0
        assertEquals("Database Management", allAssignments.get(0).getTitle());
        assertEquals("Web Development", allAssignments.get(1).getTitle());

    }


    @Test
    @Transactional
    void updateAssignment() {
        // Arrange - assignment1 is set up in @BeforeEach
        assignment1.setTitle("Updated Assignment");
        assignment1.setAdditionalInfo("Updated Description");
        assignment1.setDate(LocalDate.now().plusDays(7));

        // Make sure assignment1 has a valid Nonprofit associated with it
        assignment1.setNonprofit(testNonprofit);

        // Act - Call the updateAssignment() method
        assignmentDao.updateAssignment(assignment1);

        // Assert - Get the updated assignment by ID and verify the changes
        Assignment updatedAssignment = assignmentDao.getAssignmentByID(assignment1.getId());
        assertNotNull(updatedAssignment);
        assertEquals("Updated Assignment", updatedAssignment.getTitle());
        assertEquals("Updated Description", updatedAssignment.getAdditionalInfo());
        assertEquals(LocalDate.now().plusDays(7), updatedAssignment.getDate());

        // You can also assert the Nonprofit ID if needed
        assertNotNull(updatedAssignment.getNonprofit());
        assertEquals(testNonprofit.getId(), updatedAssignment.getNonprofit().getId());

        assertEquals(testNonprofit.getCompanyName(), updatedAssignment.getNonprofit().getCompanyName());
    }


    @Test
    @Transactional
    void deleteAssignmentByID() {
        // Arrange - assignment1 is set up in @BeforeEach

        // Act - Call the deleteAssignmentByID() method to delete the assignment
        assignmentDao.deleteAssignmentByID(assignment1.getId());

        // Assert - Try to retrieve the deleted assignment by ID, it should be null
        Assignment deletedAssignment = assignmentDao.getAssignmentByID(assignment1.getId());
        assertNull(deletedAssignment);
    }
}