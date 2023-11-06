package com.project.SchoolManagementSystem.teachers.Service;

import com.project.SchoolManagementSystem.exception.ResourceNotFoundException;
import com.project.SchoolManagementSystem.teachers.Dto.TeacherDetailsDto;
import com.project.SchoolManagementSystem.teachers.Dto.TeacherResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface TeacherService {
    ResponseEntity<List<TeacherResponseDto>> getAllTeachers() throws ResourceNotFoundException;

    ResponseEntity<List<TeacherResponseDto>> getTeachersByPage(Integer pageNo) throws ResourceNotFoundException;

    ResponseEntity<String> createTeacher(MultipartFile image, String teacherData) throws ResourceNotFoundException, IOException;

    ResponseEntity<TeacherDetailsDto> getTeacherById(Integer teacherId) throws ResourceNotFoundException, IOException;

    ResponseEntity<Integer> getTeacherByUserId(Integer userId) throws ResourceNotFoundException;

    ResponseEntity<String> updateTeacher(MultipartFile image, String teacherData) throws ResourceNotFoundException, IOException;

    ResponseEntity<Object> deleteTeacher(Integer teacherId) throws ResourceNotFoundException;

    ResponseEntity<String> assignSubjectToTeacher(Integer teacherId, Integer SubjectId) throws ResourceNotFoundException;

    ResponseEntity<String> assignClassToTeacher(Integer teacherId, Integer classId) throws ResourceNotFoundException;

    ResponseEntity<List<TeacherResponseDto>> getTeachersByQualification(String qualification) throws ResourceNotFoundException;
}
