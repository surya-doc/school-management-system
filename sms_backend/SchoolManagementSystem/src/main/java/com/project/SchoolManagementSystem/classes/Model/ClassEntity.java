package com.project.SchoolManagementSystem.classes.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.SchoolManagementSystem.students.Model.StudentEntity;
import com.project.SchoolManagementSystem.teachers.Model.TeacherEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity(name = "class")
@Table(name = "class")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ClassEntity {
    @Id
    @Column(name="class_id")
    private Integer class_id;

    @Column(name = "class_name")
    private String class_name;

    @Column(name = "min_qualification")
    private String min_qualification;

    @Column(name = "teacher_id")
    private Integer teacher_id;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "teachers_classes",
            joinColumns = @JoinColumn(name = "class_id"),
            inverseJoinColumns = @JoinColumn(name = "teacher_id")
    )
    private List<TeacherEntity> teacherEntityList;
    @JsonIgnore
    @OneToMany(mappedBy = "student_class")
    private List<StudentEntity> students;
}
