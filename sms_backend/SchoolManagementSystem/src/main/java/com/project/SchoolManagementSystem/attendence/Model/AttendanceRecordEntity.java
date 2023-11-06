package com.project.SchoolManagementSystem.attendence.Model;

import com.project.SchoolManagementSystem.students.Model.StudentEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity(name = "attendance_record")
@Table(name = "attendance_record")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AttendanceRecordEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name="student_id")
    private StudentEntity students;

    @Column(name = "date")
    private Date date;

    @Column(name = "status")
    private Boolean status;

}
