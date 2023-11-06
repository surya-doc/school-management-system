package com.project.SchoolManagementSystem.users.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UpdatePasswordRequestDto {
    private String oldPassword;
    private String newPassword;
}