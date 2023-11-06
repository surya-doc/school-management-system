package com.project.SchoolManagementSystem.resetPassword.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "reset_password")
@Table(name = "reset_password")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="user_id")
    private Integer user_id;

    @Column(name="otp")
    private String otp;
}
