-- Inserting data into the `skill` table
INSERT INTO skill (title, additionalInfo) VALUES
    ('Web Development', 'Experience in building websites using HTML, CSS, and JavaScript'),
    ('Database Management', 'Knowledge of SQL and database design'),
    ('Social Media Marketing', 'Experience in managing social media accounts and running ad campaigns'),
    ('Graphic Design', 'Proficiency in Adobe Photoshop and Illustrator'),
    ('Fundraising', 'Experience in organizing fundraising events and campaigns'),
    ('Event Planning', 'Skills in coordinating and planning events'),
    ('Data Analysis', 'Ability to analyze and interpret data using tools like Excel or Python'),
    ('Public Speaking', 'Confident in delivering presentations and speaking in public'),
    ('Grant Writing', 'Experience in writing grant proposals for funding opportunities'),
    ('Volunteer Coordination', 'Skills in managing and coordinating volunteer activities');
    
    -- Inserting data into the `Nonprofit` table
INSERT INTO Nonprofit (companyName, phoneNum, email, address, mission) VALUES
    ('Charity for All', '123-456-7890', 'info@charityforall.org', '123 Main St, Greensboro, NC', 'Supporting underprivileged children'),
    ('Environmental Guardians', '987-654-3210', 'contact@enviroguardians.org', '456 Oak Ave, Cary, NC', 'Promoting environmental sustainability'),
    ('Helping Hands Foundation', '555-888-9999', 'foundation@helpinghands.org', '789 Elm Rd, Charlotte, NC', 'Assisting the elderly and disabled'),
    ('Tech4Good', '111-222-3333', 'info@tech4good.org', '321 Pine Blvd, Raleigh, NC', 'Using technology for social impact'),
    ('Animal Rescue League', '444-333-5555', 'contact@animalrescue.org', '567 Maple St, Asheville, NC', 'Rescuing and rehabilitating animals');

-- Inserting data into the `Assignment` table
INSERT INTO Assignment (title, additionalInfo, `date`, nonprofitID) VALUES
    ('Web Development', 'Frontend and backend work', '2023-07-10', 1),
    ('Database Management', 'Optimizing queries', '2023-07-15', 2),
    ('Marketing Campaign', 'Social media promotion', '2023-07-20', 3),
    ('Graphic Design', 'Designing brochures', '2023-07-25', 4),
    ('Data Analysis', 'Analyzing survey results', '2023-07-30', 5);
    
INSERT INTO Volunteer (firstName, lastName, phoneNum, email, city, state, skillID) VALUES
    ('John', 'Doe', '555-123-4567', 'john.doe@example.com', 'New York', 'NY', 1),
    ('Jane', 'Smith', '987-654-3210', 'jane.smith@example.com', 'Los Angeles', 'CA', 2),
    ('Michael', 'Johnson', '444-555-6666', 'michael.johnson@example.com', 'Chicago', 'IL', 3),
    ('Emily', 'Williams', '777-888-9999', 'emily.williams@example.com', 'Houston', 'TX', 4),
    ('Daniel', 'Brown', '222-333-4444', 'daniel.brown@example.com', 'Miami', 'FL', 5),
    ('Olivia', 'Lee', '999-888-7777', 'olivia.lee@example.com', 'Seattle', 'WA', 6),
    ('Sophia', 'Miller', '111-222-3333', 'sophia.miller@example.com', 'San Francisco', 'CA', 7),
    ('William', 'Wilson', '444-333-2222', 'william.wilson@example.com', 'Boston', 'MA', 8),
    ('Ava', 'Martinez', '666-777-8888', 'ava.martinez@example.com', 'Phoenix', 'AZ', 9),
    ('James', 'Anderson', '888-999-0000', 'james.anderson@example.com', 'Denver', 'CO', 10);

-- Inserting data into the `Timesheet` table
INSERT INTO Timesheet (hoursLogged, `date`, volunteerID, assignmentID) VALUES
    ('4', '2023-07-11', 1, 1),
    ('6', '2023-07-12', 2, 1),
    ('5', '2023-07-15', 3, 2),
    ('3', '2023-07-17', 4, 2),
    ('2', '2023-07-20', 5, 3),
    ('7', '2023-07-21', 6, 3),
    ('8', '2023-07-26', 7, 4),
    ('4', '2023-07-27', 8, 4),
    ('6', '2023-07-31', 9, 5),
    ('3', '2023-07-31', 10, 5);

-- In this UPDATE statement, we are using a JOIN between the Volunteer and Timesheet tables on their respective volunteerID columns. This allows us to match each volunteer with the corresponding timesheet entry. The SET clause assigns the timesheetID value from the Timesheet table to the timesheetID column in the Volunteer table. The WHERE clause restricts the update only to rows where the timesheetID in the Volunteer table is NULL, preventing the overwriting of existing values.
-- This way, you can update the timesheetID column in the Volunteer table based on the data from the Timesheet table for the corresponding volunteers. Make sure to back up your data before running any update statements to prevent data loss in case of mistakes.

UPDATE Volunteer AS v
JOIN Timesheet AS t ON v.volunteerID = t.volunteerID
SET v.timesheetID = t.timesheetID
WHERE v.timesheetID IS NULL
-- use a KEY column in the WHERE clause of the UPDATE statement. Since volunteerID is the primary key in the Volunteer table, we can use it in the WHERE clause to target specific rows
-- the volunteerID from the Volunteer table as a KEY column in the WHERE clause. We also ensure that the volunteerID exists in the Timesheet table by using the IN subquery. This way, we avoid updating rows that don't have a corresponding timesheetID.
AND v.volunteerID IN (SELECT volunteerID FROM Timesheet);
