package com.project.SchoolManagementSystem.attendence.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceRecordDto {
    private Integer student_id;
    private BigDecimal present_days;
    private Long total_days;
    private Integer user_id;
    private String name;
}
