package com.project.SchoolManagementSystem.students.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StudentResponseDto {
    private Integer user_id;
    private String email;
    private String full_name;
    private String ph_number;
    private Date dob;
    private String gender;
    private String role;
    private byte[] profile_pic;
}
