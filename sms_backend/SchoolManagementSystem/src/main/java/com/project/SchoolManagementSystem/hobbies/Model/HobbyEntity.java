package com.project.SchoolManagementSystem.hobbies.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.SchoolManagementSystem.students.Model.StudentEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity(name = "hobbies")
@Table(name = "hobbies")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class HobbyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hobby_id")
    private Integer id;

    @Column(name = "hobbies")
    private String hobbies;

    @JsonIgnore
    @ManyToMany(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH
    })
    @JoinTable(
            name = "student_hobbies",
            joinColumns = @JoinColumn(name = "hobby_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private List<StudentEntity> students;

}
