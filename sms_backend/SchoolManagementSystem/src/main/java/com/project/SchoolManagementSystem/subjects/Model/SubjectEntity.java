package com.project.SchoolManagementSystem.subjects.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.SchoolManagementSystem.teachers.Model.TeacherEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity(name = "subject")
@Table(name = "subject")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SubjectEntity {
    @Id
    @Column(name="subject_id")
    private Integer subject_id;

    @Column(name = "subject_name")
    private String subject_name;

    @JsonIgnore
    @ManyToMany(mappedBy = "subjectEntityList")
    private List<TeacherEntity> teacherEntityList;
}
