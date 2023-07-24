DROP DATABASE IF EXISTS volunteerManagementSystem;
CREATE DATABASE volunteerManagementSystem;
USE volunteerManagementSystem;

-- for testing:
-- DROP DATABASE IF EXISTS volunteerManagementSystemTest;
-- CREATE DATABASE volunteerManagementSystemTest;
-- USE volunteerManagementSystemTest;

CREATE TABLE IF NOT EXISTS skill (
	skillID INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
	title VARCHAR(50) NOT NULL,
	additionalInfo VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS Volunteer (
	volunteerID INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
	firstName VARCHAR(20) NOT NULL,
	lastName VARCHAR(30) NOT NULL,
	phoneNum CHAR(10) NOT NULL,
	email VARCHAR(30) NOT NULL,
    city VARCHAR(25) NOT NULL,
    state CHAR(2) NOT NULL,
    skillID int NOT NULL,
    timesheetID int NOT NULL,
    FOREIGN KEY (skillID) REFERENCES skill(skillID)
);
DESCRIBE Volunteer;

CREATE TABLE IF NOT EXISTS Nonprofit (
	nonprofitID INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
	companyName VARCHAR(250) NOT NULL,
	phoneNum CHAR(10) NOT NULL,
	email VARCHAR(40) NOT NULL,
    address varchar(255) NOT NULL,
    mission VARCHAR(255) NOT NULL
  );

  CREATE TABLE IF NOT EXISTS Assignment (
	assignmentID INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
	title VARCHAR(100) NOT NULL,
	additionalInfo VARCHAR(255),
	`date` DATETIME NOT NULL,
    nonprofitID int,
	FOREIGN KEY (nonprofitID) REFERENCES Nonprofit(nonprofitID)
);
DESCRIBE Assignment;

CREATE TABLE IF NOT EXISTS Timesheet (
	timesheetID INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
	hoursLogged int NOT NULL,
    `date` DATETIME NOT NULL,
	volunteerID int NOT NULL,
    assignmentID int NOT NULL,
    FOREIGN KEY (volunteerID) REFERENCES Volunteer(volunteerID),
    FOREIGN KEY (assignmentID) REFERENCES Assignment(assignmentID)
  );
  DESCRIBE Timesheet;

  ALTER TABLE Volunteer
	ADD CONSTRAINT
    FOREIGN KEY FK_Timesheet_Volunteer(timesheetID)
    REFERENCES Timesheet (timesheetID);

  -- bridge table for many to many relationship
CREATE TABLE IF NOT EXISTS Nonprofit_Volunteer (
	nonprofitID INT NOT NULL,
	volunteerID INT NOT NULL,
	PRIMARY KEY pk_NonprofitVolunteer (nonprofitID, volunteerID)
);

USE volunteerManagementSystem;
SELECT * FROM assignment;
SELECT * FROM nonprofit;
SELECT * FROM nonprofit_volunteer;
SELECT * FROM skill;
SELECT * FROM timesheet;
SELECT * FROM volunteer;