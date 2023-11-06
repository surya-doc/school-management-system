package com.project.SchoolManagementSystem.classes.Service;

import com.project.SchoolManagementSystem.classes.Dto.ClassResDto;
import com.project.SchoolManagementSystem.classes.Model.ClassEntity;
import com.project.SchoolManagementSystem.exception.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

public interface ClassService {


    ResponseEntity<List<ClassResDto>> getAllClasses() throws ResourceNotFoundException;
    ResponseEntity<List<ClassResDto>> getAllClassesByPage(Integer pageNo) throws ResourceNotFoundException;
    ResponseEntity<ClassEntity> getClassById(Integer classId) throws ResourceNotFoundException;
    ResponseEntity<String> createClass(ClassEntity classEntity) throws ResourceNotFoundException;
    ResponseEntity<String> updateClass(ClassEntity classEntity) throws ResourceNotFoundException;
    ResponseEntity<String> deleteClass(Integer classId) throws ResourceNotFoundException;
    ResponseEntity<List<ClassResDto>> getClassesByQualification(String qualification);
    ResponseEntity<Integer> getClassByClassTeacherId(Integer teacher_id) throws ResourceNotFoundException;
    ResponseEntity<Object> assignClassTeacher(Integer classId, Integer teacherId) throws ResourceNotFoundException, IOException;
}
