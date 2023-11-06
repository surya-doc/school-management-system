package com.project.SchoolManagementSystem.attendence.Model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity(name = "attendance")
@Table(name = "attendance")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AttendanceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name="student_id")
    private Integer student_id;

    @Column(name = "date")
    private Date date;

    @Column(name = "status")
    private Boolean status;

}
