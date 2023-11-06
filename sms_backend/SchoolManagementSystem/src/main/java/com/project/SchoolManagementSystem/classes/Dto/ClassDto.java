package com.project.SchoolManagementSystem.classes.Dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ClassDto {
    private Integer class_id;
    private String class_name;
    private String min_qualification;
    private Integer teacher_id;
}
