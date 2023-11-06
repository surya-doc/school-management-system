package com.project.SchoolManagementSystem.teachers.Dto;

import com.project.SchoolManagementSystem.address.Dto.UserAddressDto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
//This is what I am getting from frontend at the time of creation
public class CreateTeacherDataDto {
    private Integer id;
    private String fullName;
    private String dob;
    private String email;
    private String phoneNumber;
    private String qualification;
    private String[] subjects;
    private String[] classes;
    private String gender;
    private UserAddressDto current_Address;
    private UserAddressDto permanent_Address;
    private String profilePic;
}
