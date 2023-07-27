

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

INSERT INTO Volunteer (firstName, lastName, phoneNum, email, city, state) VALUES
    ('John', 'Doe', '555-123-4567', 'john.doe@example.com', 'New York', 'NY'),
    ('Jane', 'Smith', '987-654-3210', 'jane.smith@example.com', 'Los Angeles', 'CA'),
    ('Michael', 'Johnson', '444-555-6666', 'michael.johnson@example.com', 'Chicago', 'IL'),
    ('Emily', 'Williams', '777-888-9999', 'emily.williams@example.com', 'Houston', 'TX'),
    ('Daniel', 'Brown', '222-333-4444', 'daniel.brown@example.com', 'Miami', 'FL'),
    ('Olivia', 'Lee', '999-888-7777', 'olivia.lee@example.com', 'Seattle', 'WA'),
    ('Sophia', 'Miller', '111-222-3333', 'sophia.miller@example.com', 'San Francisco', 'CA'),
    ('William', 'Wilson', '444-333-2222', 'william.wilson@example.com', 'Boston', 'MA'),
    ('Ava', 'Martinez', '666-777-8888', 'ava.martinez@example.com', 'Phoenix', 'AZ'),
    ('James', 'Anderson', '888-999-0000', 'james.anderson@example.com', 'Denver', 'CO');
    
    -- Inserting data into the `skill` table
INSERT INTO Skill (title, additionalInfo, volunteerID) VALUES
    ('Web Development', 'Experience in building websites using HTML, CSS, and JavaScript', 1),
    ('Database Management', 'Knowledge of SQL and database design', 5),
    ('Social Media Marketing', 'Experience in managing social media accounts and running ad campaigns', 9),
    ('Graphic Design', 'Proficiency in Adobe Photoshop and Illustrator', 1),
    ('Fundraising', 'Experience in organizing fundraising events and campaigns', 2),
    ('Event Planning', 'Skills in coordinating and planning events', 3),
    ('Data Analysis', 'Ability to analyze and interpret data using tools like Excel or Python', 7);
--    ('Public Speaking', 'Confident in delivering presentations and speaking in public', 8),
--    ('Grant Writing', 'Experience in writing grant proposals for funding opportunities', 9),
--    ('Volunteer Coordination', 'Skills in managing and coordinating volunteer activities', 10);

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


INSERT INTO Nonprofit_Volunteer (nonprofitID, volunteerID) VALUES
    (1, 1),
    (1, 2),
    (1, 3),
    (2, 2),
    (2, 4),
    (3, 3),
    (4, 1),
    (4, 4),
    (4, 5);
