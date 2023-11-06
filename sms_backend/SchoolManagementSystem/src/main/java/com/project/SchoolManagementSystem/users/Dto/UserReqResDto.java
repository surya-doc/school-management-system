package com.project.SchoolManagementSystem.users.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserReqResDto {
    private Integer user_id;
    private String email;
    private String full_name;
    private String ph_number;
    private Date dob;
    private String gender;
    private String role;
    private String profile_pic;
}
