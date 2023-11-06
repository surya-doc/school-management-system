package com.project.SchoolManagementSystem.students.Dto;

import com.project.SchoolManagementSystem.address.Dto.UserAddressDto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CreateStudentDataDto {
    private Integer id;
    private String fullName;
    private String dob;
    private String email;
    private String phoneNumber;
    private String gender;
    private Integer classId;
    private UserAddressDto current_Address;
    private UserAddressDto permanent_Address;
    private String[] hobbies;
    private String profilePic;
}
