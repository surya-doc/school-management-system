package com.project.SchoolManagementSystem.teachers.Dto;

import com.project.SchoolManagementSystem.address.Dto.UserAddressDto;
import com.project.SchoolManagementSystem.classes.Model.ClassEntity;
import com.project.SchoolManagementSystem.subjects.Model.SubjectEntity;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TeacherDetailsDto {
    private Integer id;
    private String fullName;
    private Date dob;
    private String email;
    private String phoneNumber;
    private String qualification;
    private SubjectEntity[] subjects;
    private ClassEntity[] classes;
    private String gender;
    private UserAddressDto current_address;
    private UserAddressDto permanent_address;
    private byte[] profile_pic;
}
