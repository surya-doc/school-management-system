package com.project.SchoolManagementSystem.hobbies.Service.Impl;

import com.project.SchoolManagementSystem.exception.ResourceNotFoundException;
import com.project.SchoolManagementSystem.hobbies.Model.HobbyEntity;
import com.project.SchoolManagementSystem.hobbies.Service.HobbyService;
import com.project.SchoolManagementSystem.students.Dao.StudentDao;
import com.project.SchoolManagementSystem.students.Model.StudentEntity;
import com.project.SchoolManagementSystem.students.Service.Impl.StudentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
public class HobbyServiceImpl implements HobbyService {
    private StudentServiceImpl studentService;
    private StudentDao studentDao;

    @Autowired
    public HobbyServiceImpl(StudentServiceImpl studentService, StudentDao studentDao) {
        this.studentService = studentService;
        this.studentDao = studentDao;
    }

    @Override
    public ResponseEntity<String> createUserHobbies(Integer userId, HobbyEntity hobby) throws ResourceNotFoundException, IOException {
        StudentEntity student = this.studentDao.findByUserId(userId);
        student.getStudent_hobbies().add(hobby);
        this.studentDao.save(student);
        return ResponseEntity.ok("Created a new hobby for user with id:"+userId);
    }

    @Override
    public ResponseEntity<List<HobbyEntity>> getUserHobbies(Integer UserId) throws ResourceNotFoundException {
        List<HobbyEntity> hobbies= Objects.requireNonNull(this.studentDao.findByUserId(UserId)).getStudent_hobbies();
        return ResponseEntity.ok(hobbies);
    }

    @Override
    public ResponseEntity<String> deleteUserHobbies(Integer userId,HobbyEntity hobby) throws ResourceNotFoundException {
        List<HobbyEntity> hobbies= Objects.requireNonNull(this.studentDao.findByUserId(userId)).getStudent_hobbies();
        List<HobbyEntity> hobbies1 = hobbies.stream().filter(e->e!=hobby).toList();
        return ResponseEntity.ok("Deleted a Hobby for user "+userId);
    }
}
