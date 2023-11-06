package com.project.SchoolManagementSystem.students.Dto;


import com.project.SchoolManagementSystem.attendence.Model.AttendanceRecordEntity;
import com.project.SchoolManagementSystem.classes.Model.ClassEntity;
import com.project.SchoolManagementSystem.hobbies.Model.HobbyEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StudentDto {
    private Integer student_id;
    private byte[] dob_certificate;
    private ClassEntity class_id;
    private List<HobbyEntity> hobbbies;
    private List<AttendanceRecordEntity> attendence;
    private Integer user_id;
}
