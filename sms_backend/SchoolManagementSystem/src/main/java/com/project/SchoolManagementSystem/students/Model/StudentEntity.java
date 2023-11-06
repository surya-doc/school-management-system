package com.project.SchoolManagementSystem.students.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.project.SchoolManagementSystem.attendence.Model.AttendanceRecordEntity;
import com.project.SchoolManagementSystem.classes.Model.ClassEntity;
import com.project.SchoolManagementSystem.hobbies.Model.HobbyEntity;
import com.project.SchoolManagementSystem.users.Model.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity(name = "students")
@Table(name = "students")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="student_id")
    private Integer student_id;

    @Column(name = "dob_certificate")
    private String dob_certificate;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "class_id")
    private ClassEntity student_class;

    @JsonIgnore
    @ManyToMany(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH
    })
    @JoinTable(name = "student_hobbies",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name="hobby_id"))
    private List<HobbyEntity> student_hobbies;


    @OneToMany(mappedBy = "students")
    private List<AttendanceRecordEntity> student_attendance;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UserEntity user_id;
}
