package com.project.SchoolManagementSystem.users.Service;

import com.project.SchoolManagementSystem.exception.ResourceNotFoundException;
import com.project.SchoolManagementSystem.users.Dto.UserDto;
import com.project.SchoolManagementSystem.users.Dto.UserLoginDto;
import com.project.SchoolManagementSystem.users.Dto.UserReqResDto;
import com.project.SchoolManagementSystem.users.Model.UserEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {
    ResponseEntity<List<UserReqResDto>> getAllUsers() throws ResourceNotFoundException;

    ResponseEntity<List<UserReqResDto>> getAllStudentsByPage(Integer pageNo) throws ResourceNotFoundException;

    ResponseEntity<List<UserReqResDto>> getAllTeachersByPage(Integer pageNo) throws ResourceNotFoundException;

    ResponseEntity<String> createUser(UserDto user) throws ResourceNotFoundException;

    ResponseEntity<UserReqResDto> getUserById(Integer userId) throws ResourceNotFoundException;

    ResponseEntity<String> updateUser(UserReqResDto user) throws ResourceNotFoundException;

    ResponseEntity<String> deleteUser(Integer userId) throws ResourceNotFoundException;

    ResponseEntity<UserEntity> getUserDetailsById(Integer userId) throws ResourceNotFoundException;

    ResponseEntity<String> updateUserDetails(UserEntity user) throws ResourceNotFoundException;

    ResponseEntity<UserLoginDto> getUserDetailsByEmail(String email) throws UsernameNotFoundException;

    ResponseEntity<Object> updateUserProfilePic(MultipartFile profilePic, Integer userId) throws UsernameNotFoundException, IOException, ResourceNotFoundException;
}
