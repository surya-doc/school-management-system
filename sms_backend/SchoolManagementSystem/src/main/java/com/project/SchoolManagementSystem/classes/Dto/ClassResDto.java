package com.project.SchoolManagementSystem.classes.Dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ClassResDto {
    private Integer class_id;
    private String class_name;
    private String min_qualification;
    private String teacher_name;
    private Integer total_students;
}
