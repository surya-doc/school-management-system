package com.project.SchoolManagementSystem.hobbies.Dto;

import com.project.SchoolManagementSystem.students.Model.StudentEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HobbyDto {
    private Integer id;
    private String hobby;
    private List<StudentEntity> students;
}
