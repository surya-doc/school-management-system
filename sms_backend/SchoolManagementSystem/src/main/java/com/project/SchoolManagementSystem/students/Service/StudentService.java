package com.project.SchoolManagementSystem.students.Service;

import com.project.SchoolManagementSystem.exception.ResourceNotFoundException;
import com.project.SchoolManagementSystem.students.Dto.StudentDetailsDto;
import com.project.SchoolManagementSystem.students.Dto.StudentResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface StudentService {
    public ResponseEntity<List<StudentResponseDto>> getAllStudents() throws ResourceNotFoundException;
    ResponseEntity<List<StudentResponseDto>> getAllStudentsByPages(Integer pageNo) throws ResourceNotFoundException;
    public StudentDetailsDto getStudentById(Integer id) throws ResourceNotFoundException, IOException;
    public ResponseEntity<String> createStudent(MultipartFile image, String studentData) throws ResourceNotFoundException, IOException;
    public ResponseEntity<String> updateStudentById(MultipartFile image, String studentData) throws ResourceNotFoundException, IOException;
    public ResponseEntity<Object> deleteStudentById(Integer id) throws ResourceNotFoundException;
    public ResponseEntity<Object> addHobby(Integer studentId, String hobby) throws ResourceNotFoundException;
    public ResponseEntity<List<StudentResponseDto>> getStudentsByClassId(Integer classId, Integer pageNo) throws ResourceNotFoundException;
    public ResponseEntity<Object> addDobCertificate(Integer studentId, MultipartFile dobCertificate) throws ResourceNotFoundException, IOException;
    public ResponseEntity<List<StudentResponseDto>> getStudentsByAttendance(Integer classId) throws ResourceNotFoundException;
    public ResponseEntity<Integer> getStudentDetailByUserId(Integer userId) throws ResourceNotFoundException;
}
