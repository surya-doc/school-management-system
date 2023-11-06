package com.project.SchoolManagementSystem.teachers.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.SchoolManagementSystem.classes.Model.ClassEntity;
import com.project.SchoolManagementSystem.subjects.Model.SubjectEntity;
import com.project.SchoolManagementSystem.users.Model.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity(name = "teacher")
@Table(name = "teacher")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TeacherEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teacher_id")
    private Integer teacher_id;

    @Column(name = "qualification")
    private String qualification;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UserEntity user_id;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "teachers_classes",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "class_id")
    )
    private List<ClassEntity> classEntityList;

    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "teachers_subjects",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    private List<SubjectEntity> subjectEntityList;
}
