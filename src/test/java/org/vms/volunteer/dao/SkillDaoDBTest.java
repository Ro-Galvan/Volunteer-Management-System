package org.vms.volunteer.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.vms.volunteer.dto.Nonprofit;
import org.vms.volunteer.dto.Skill;
import org.vms.volunteer.dto.Volunteer;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class SkillDaoDBTest {
    @Autowired
    SkillDao skillDao;

    @Autowired
    VolunteerDao volunteerDao;

    @Autowired
    private NonprofitDao nonprofitDao;
    private Volunteer testVolunteer;

    @BeforeEach
    void setUp() {
        // Delete all nonprofits
        List<Nonprofit> nonprofits = nonprofitDao.getAllNonprofits();
        nonprofits.forEach(nonprofit -> nonprofitDao.deleteNonprofitByID(nonprofit.getId()));

        // Create a new Nonprofit
        Nonprofit testNonprofit = new Nonprofit("Test Nonprofit", "555-789-1234", "test@example.com", "Test Address", "Test Mission");
        // Add the Nonprofit to the database
        testNonprofit = nonprofitDao.addNonprofit(testNonprofit);

        // Create a new Volunteer
        Volunteer testVolunteer = new Volunteer("John", "Doe", "555-123-4567", "john.doe@example.com", "New York", "NY", new ArrayList<>());
        // Add the Volunteer to the database
        testVolunteer = volunteerDao.addVolunteer(testVolunteer);

        // Add the Nonprofit to the Volunteer's list of nonprofits
        testVolunteer.getNonprofits().add(testNonprofit);

        // Save the Volunteer object for use in the test methods
        this.testVolunteer = testVolunteer;

        // Delete all Skills
        List<Skill> skills = skillDao.getAllSkills();
//        enhanced for loop" or "foreach loop" that is  iterating over elements in a collection or an array. The arrow (->) used in this context is part of Java's lambda expression syntax, which is used to define a lambda or an anonymous function.
        skills.forEach(skill -> skillDao.deleteSkillByID(skill.getId()));

        // Delete all Volunteers
        List<Volunteer> volunteers = volunteerDao.getAllVolunteers();
        volunteers.forEach(volunteer -> volunteerDao.deleteVolunteerByID(volunteer.getId()));
    }

//    @Test
//    void addSkill() {
//        // Arrange - using the Volunteer object set up in @BeforeEach & using the Skill constructor
//        Volunteer volunteer = new Volunteer("John", "Doe", "555-123-4567", "john.doe@example.com", "New York", "NY", null);
//        volunteer = volunteerDao.addVolunteer(volunteer);
//
//        Skill skill = new Skill("Fundraising", "Experience in organizing fundraising events and campaigns", volunteer);
//
//        // Act - Call the addSkill() method
//        skill = skillDao.addSkill(skill);
//
//        // Assert
//        assertNotNull(skill);
//        assertNotNull(skill.getId());
//        assertEquals("Fundraising", skill.getTitle());
//        assertEquals("Experience in organizing fundraising events and campaigns", skill.getAdditionalInfo());
//
//        // Verify the Volunteer associated with the Skill
//        assertNotNull(skill.getVolunteer());
//        assertEquals(volunteer.getId(), skill.getVolunteer().getId());
//        assertEquals(volunteer.getFirstName(), skill.getVolunteer().getFirstName());
//        assertEquals(volunteer.getLastName(), skill.getVolunteer().getLastName());
//        assertEquals(volunteer.getEmail(), skill.getVolunteer().getEmail());
//    }


    @Test
    void addSkill() {
//        Create the Volunteer object first, then use it to create the Skill object for testing.
        // Arrange - Create a new Volunteer and add it to the database
        Volunteer testVolunteer = new Volunteer("John", "Doe", "555-123-4567", "john.doe@example.com", "New York", "NY", new ArrayList<>());
        testVolunteer = volunteerDao.addVolunteer(testVolunteer);

        // Create a new Skill associated with the Volunteer
        Skill skill = new Skill("Fundraising", "Experience in organizing fundraising events and campaigns", testVolunteer);

        // Act -- Call the addSkill() method
        skill = skillDao.addSkill(skill);

        // Assert
        assertNotNull(skill);
        assertNotNull(skill.getId());
        assertEquals("Fundraising", skill.getTitle());
        assertEquals("Experience in organizing fundraising events and campaigns", skill.getAdditionalInfo());

        // Verify the Volunteer associated with the Skill
        assertNotNull(skill.getVolunteer());
        assertEquals(testVolunteer.getId(), skill.getVolunteer().getId());
        assertEquals(testVolunteer.getFirstName(), skill.getVolunteer().getFirstName());
        assertEquals(testVolunteer.getLastName(), skill.getVolunteer().getLastName());
    }




    @Test
    void getSkillByID() {
        // Arrange - Create a new Volunteer and add it to the database
        Volunteer testVolunteer = new Volunteer("John", "Doe", "555-123-4567", "john.doe@example.com", "New York", "NY", new ArrayList<>());
        testVolunteer = volunteerDao.addVolunteer(testVolunteer);

        // Create a new Skill and associate it with the Volunteer
        Skill skillToAdd = new Skill("Test Skill", "Test Additional Info", testVolunteer);
        skillToAdd = skillDao.addSkill(skillToAdd);

        // Act - Call the getSkillByID() method to retrieve the Skill from the database
        Skill retrievedSkill = skillDao.getSkillByID(skillToAdd.getId());

        // Assert - Check if the retrievedSkill is not null and its properties match the original skillToAdd
        assertNotNull(retrievedSkill);
        assertEquals(skillToAdd.getId(), retrievedSkill.getId());
        assertEquals(skillToAdd.getTitle(), retrievedSkill.getTitle());
        assertEquals(skillToAdd.getAdditionalInfo(), retrievedSkill.getAdditionalInfo());

        // If you have associations like Volunteer, you can also check them here
        assertNotNull(retrievedSkill.getVolunteer());
        assertEquals(testVolunteer.getId(), retrievedSkill.getVolunteer().getId());
        // Add additional assertions as needed for other properties of the Volunteer object
    }

    @Test
    void getAllSkills() {
    }

    @Test
    void updateSkill() {
    }

    @Test
    void deleteSkillByID() {
    }
}