package org.vms.volunteer.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
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
    private Skill testSkill;

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

//        // Create a new Skill associated with the Volunteer
//        Skill testSkill = new Skill("Test Skill", "Test Additional Info", testVolunteer);
//        // Add the Skill to the database
//        testSkill = skillDao.addSkill(testSkill);

        // Save the Volunteer and Skill objects for use in the test methods
        this.testVolunteer = testVolunteer;
//        this.testSkill = testSkill;
    
    }



    @Test
    @Transactional
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
    @Transactional
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
    @Transactional
    void getAllSkills() {
        // Arrange - Create some skills and add them to the database, associated with the testVolunteer
        Skill skill1 = new Skill("Skill 1", "Additional Info 1", testVolunteer);
        skill1 = skillDao.addSkill(skill1);

        Skill skill2 = new Skill("Skill 2", "Additional Info 2", testVolunteer);
        skill2 = skillDao.addSkill(skill2);

        // Act - Call the getAllSkills() method to retrieve skills associated with testVolunteer
        List<Skill> skills = skillDao.getAllSkills();

        // Assert - Check if the list of skills is not null and contains the expected number of skills
        assertNotNull(skills);
        assertEquals(2, skills.size());

        // Verify that all the retrieved skills are associated with the testVolunteer
        for (Skill skill : skills) {
            assertEquals(testVolunteer.getId(), skill.getVolunteer().getId());
            assertEquals(testVolunteer.getFirstName(), skill.getVolunteer().getFirstName());
            assertEquals(testVolunteer.getLastName(), skill.getVolunteer().getLastName());
            // Add additional assertions as needed for other properties of the Volunteer object
        }
    }

    @Test
    @Transactional
    void updateSkill() {
        // Arrange - Create a new skill and add it to the database
        Skill skill = new Skill("Original Title", "Original Additional Info", testVolunteer);
        skill = skillDao.addSkill(skill);

        // Modify the skill's properties
        skill.setTitle("Updated Title");
        skill.setAdditionalInfo("Updated Additional Info");

        // Act - Call the updateSkill() method to update the skill in the database
        skillDao.updateSkill(skill);

        // Retrieve the skill from the database again to check if it was updated
        Skill updatedSkill = skillDao.getSkillByID(skill.getId());

        // Assert - Check if the retrieved skill matches the updated skill
        assertNotNull(updatedSkill.getVolunteer());
        assertEquals(skill.getId(), updatedSkill.getId());
        assertEquals(skill.getTitle(), updatedSkill.getTitle());
        assertEquals(skill.getAdditionalInfo(), updatedSkill.getAdditionalInfo());
        // Add additional assertions as needed for other properties
    }

    @Test
    @Transactional
    void deleteSkillByID() {
        // Arrange - Create a new skill and add it to the database
        Skill skill = new Skill("Title to Delete", "Additional Info to Delete", testVolunteer);
        skill = skillDao.addSkill(skill);

        // Act - Call the deleteSkillByID() method to delete the skill from the database
        skillDao.deleteSkillByID(skill.getId());

        // Try to retrieve the skill from the database again
        Skill deletedSkill = skillDao.getSkillByID(skill.getId());

        // Assert - Check if the retrieved skill is null after deletion
        assertNull(deletedSkill);
    }

}