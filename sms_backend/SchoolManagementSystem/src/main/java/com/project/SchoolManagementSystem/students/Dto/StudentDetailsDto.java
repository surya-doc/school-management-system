package com.project.SchoolManagementSystem.students.Dto;

import com.project.SchoolManagementSystem.address.Dto.UserAddressDto;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class StudentDetailsDto {
    private Integer id;
    private String fullName;
    private Date dob;
    private String email;
    private String phoneNumber;
    private String gender;
    private Integer classDetail;
    private UserAddressDto current_Address;
    private UserAddressDto permanent_Address;
    private String[] hobbies;
    private byte[] profilePic;
    private byte[] dobCertificate;
}
