package com.project.SchoolManagementSystem.teachers.Dto;

import lombok.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TeacherResponseDto {
    private Integer user_id;
    private String email;
    private String full_name;
    private String ph_number;
    private Date dob;
    private String gender;
    private String role;
    private byte[] profile_pic;
    private String qualification;
}
