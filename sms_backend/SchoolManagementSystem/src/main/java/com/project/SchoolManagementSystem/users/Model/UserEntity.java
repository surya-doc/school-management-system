package com.project.SchoolManagementSystem.users.Model;

import com.project.SchoolManagementSystem.address.Model.AddressEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity(name = "user")
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserEntity {
    @Id
    @Column(name = "user_id")
    private Integer user_id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "full_name")
    private String full_name;

    @Column(name = "ph_number")
    private String ph_number;

    @Column(name = "dob")
    private Date dob;

    @Column(name = "gender")
    private String gender;

    @Column(name = "profile_pic")
    private String profile_pic;

    @Column(name = "role")
    private String role;

    @OneToMany(mappedBy = "user_id")
    private List<AddressEntity> user_address;

    @Override
    public String toString() {
        return "UserEntity{" +
                "user_id=" + user_id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", full_name='" + full_name + '\'' +
                ", ph_number='" + ph_number + '\'' +
                ", dob=" + dob +
                ", gender='" + gender + '\'' +
                ", profile_pic=" + profile_pic +
                ", role='" + role + '\'' +
                '}';
    }
}
