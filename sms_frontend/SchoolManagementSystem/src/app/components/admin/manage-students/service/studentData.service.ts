import { Injectable } from "@angular/core";

@Injectable()
export class ManageStudentData{
    studentData = [
        {
          id: 1,
          fullName: "Alice Johnson",
          dob: "2000-03-12",
          email: "alice.j@example.com",
          phoneNumber: "1234567890",
          gender: "Female",
          current_Address: {
            cur_street: "123 College St",
            cur_apt: "Apt 2A",
            cur_city: "Studentville",
            cur_state: "State",
            cur_country: "India",
            cur_postalCode: "736164",
            cur_address_type: "current"
          },
          permanent_Address: {
            per_street: "456 Home Rd",
            per_apt: "Unit 5",
            per_city: "Hometown",
            per_state: "State",
            per_country: "India",
            per_postalCode: "736164",
            per_address_type: "permanent"
          },
          hobbies: ["Reading", "Swimming"],
          profilePic: "",
        },
        {
          id: 2,
          fullName: "Bob Smith",
          dob: "1999-07-25",
          email: "bob.smith@email.com",
          phoneNumber: "987-654-3210",
          gender: "Male",
          current_Address: {
            cur_street: "789 University Ave",
            cur_apt: "Apt 3B",
            cur_city: "College City",
            cur_state: "State",
            cur_country: "India",
            cur_postalCode: "736164",
            cur_address_type: "current"
          },
          permanent_Address: {
            per_street: "789 University Ave",
            per_apt: "Apt 3B",
            per_city: "College City",
            per_state: "State",
            per_country: "India",
            per_postalCode: "736164",
            per_address_type: "permanent"
          },
          hobbies: ["Basketball", "Painting"],
          profilePic: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRs3DQeIV0GkNeMTW6nJkE-kRyV-lZY83URzA&usqp=CAU",
        },
        {
          id: 3,
          fullName: "Eva Martinez",
          dob: "2001-09-18",
          email: "eva.m@example.org",
          phoneNumber: "555-123-4567",
          gender: "Female",
          current_Address: {
            cur_street: "555 Dormitory Rd",
            cur_apt: "Room 101",
            cur_city: "Campusville",
            cur_state: "State",
            cur_country: "India",
            cur_postalCode: "736164",
            cur_address_type: "current"
          },
          permanent_Address: {
            per_street: "123 Family St",
            per_apt: "Unit 8",
            per_city: "Hometown",
            per_state: "State",
            per_country: "India",
            per_postalCode: "736164",
            per_address_type: "permanent"
          },
          hobbies: ["Dancing", "Singing"],
          profilePic: "",
        },
        {
          id: 4,
          fullName: "David Lee",
          dob: "2002-04-30",
          email: "david.lee@example.net",
          phoneNumber: "777-888-9999",
          gender: "Male",
          current_Address: {
            cur_street: "321 Dorm St",
            cur_apt: "Room 201",
            cur_city: "Campus City",
            cur_state: "State",
            cur_country: "India",
            cur_postalCode: "736164",
            cur_address_type: "current"
          },
          permanent_Address: {
            per_street: "321 Dorm St",
            per_apt: "Room 201",
            per_city: "Campus City",
            per_state: "State",
            per_country: "India",
            per_postalCode: "736164",
            per_address_type: "permanent"
          },
          hobbies: ["Soccer", "Gaming"],
          profilePic: "",
        },
    ];
}