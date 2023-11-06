package com.project.SchoolManagementSystem.hobbies.Service;

import com.project.SchoolManagementSystem.exception.ResourceNotFoundException;
import com.project.SchoolManagementSystem.hobbies.Model.HobbyEntity;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;

public interface HobbyService {
    public ResponseEntity<String> createUserHobbies(Integer userId, HobbyEntity hobby) throws ResourceNotFoundException, IOException;
    public ResponseEntity<List<HobbyEntity>> getUserHobbies(Integer UserId) throws ResourceNotFoundException ;
    public ResponseEntity<String> deleteUserHobbies(Integer userId, HobbyEntity hobby) throws ResourceNotFoundException ;
}
