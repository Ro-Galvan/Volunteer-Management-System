package org.vms.volunteer.dto;

import java.util.List;

public class Volunteer {
    private int id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String city;
    private String state;

//    FK associations
    private Skill skill;
    private Timesheet timesheet;


//    List<Nonprofit> nonprofits;

}
