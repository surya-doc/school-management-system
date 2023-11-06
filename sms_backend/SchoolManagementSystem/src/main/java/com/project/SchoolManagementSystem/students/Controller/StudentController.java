package com.project.SchoolManagementSystem.students.Controller;

import com.project.SchoolManagementSystem.address.Dto.AddressMapper;
import com.project.SchoolManagementSystem.address.Service.Impl.AddressServiceImpl;
import com.project.SchoolManagementSystem.exception.ResourceNotFoundException;
import com.project.SchoolManagementSystem.students.Dto.StudentDetailsDto;
import com.project.SchoolManagementSystem.students.Dto.StudentMapper;
import com.project.SchoolManagementSystem.students.Dto.StudentResponseDto;
import com.project.SchoolManagementSystem.students.Service.Impl.StudentServiceImpl;
import com.project.SchoolManagementSystem.users.Dto.UserDtoMapper;
import com.project.SchoolManagementSystem.users.Service.Impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/student")
@CrossOrigin("*")
public class StudentController {
    private StudentServiceImpl studentService;
    private StudentMapper studentMapper;
    private UserDtoMapper userDtoMapper;
    private UserServiceImpl userService;
    private AddressMapper addressMapper;
    private AddressServiceImpl addressService;

    @Autowired
    public StudentController(StudentServiceImpl studentService, StudentMapper studentMapper, UserDtoMapper userDtoMapper, UserServiceImpl userService, AddressMapper addressMapper, AddressServiceImpl addressService) {
        this.studentService = studentService;
        this.studentMapper = studentMapper;
        this.userDtoMapper = userDtoMapper;
        this.userService = userService;
        this.addressMapper = addressMapper;
        this.addressService = addressService;
    }

    @GetMapping
    public ResponseEntity<List<StudentResponseDto>> getAllStudents() throws ResourceNotFoundException {
        return this.studentService.getAllStudents();
    }

    @GetMapping("/page")
    public ResponseEntity<List<StudentResponseDto>> getAllStudentsInPages(@RequestParam(name = "pageno") int pageNo) throws ResourceNotFoundException {
        return this.studentService.getAllStudentsByPages(pageNo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDetailsDto> getStudentdetail(@PathVariable Integer id) throws ResourceNotFoundException, IOException {
        StudentDetailsDto studentDetails = this.studentService.getStudentById(id);
        return ResponseEntity.ok(studentDetails);
    }

    @Secured({"ROLE_admin","ROLE_teacher"})
    @PostMapping
    public ResponseEntity<Object> createStudent(
            @RequestParam(value = "image",required = false) MultipartFile image,
            @RequestParam("studentData") String studentData) throws ResourceNotFoundException, IOException {
        this.studentService.createStudent(image, studentData);
        System.out.println("Ok");
        return ResponseEntity.ok(Collections.singletonMap("message", "Student Created"));
    }

    @Secured({"ROLE_admin","ROLE_teacher"})
    @PutMapping
    public ResponseEntity<Object> updateStdent(
            @RequestParam(value = "image",required = false) MultipartFile image,
            @RequestParam("studentData") String studentData) throws ResourceNotFoundException, IOException {
        this.studentService.updateStudentById(image, studentData);
        System.out.println("Ok");
        return ResponseEntity.ok(Collections.singletonMap("message", "Student updated"));
    }

    @Secured({"ROLE_admin","ROLE_teacher"})
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteStudentById(@PathVariable Integer id) throws ResourceNotFoundException{
        return this.studentService.deleteStudentById(id);
    }


    @PutMapping("/hobby/{id}")
    public ResponseEntity<Object> addHobby(@PathVariable Integer id, @RequestBody String hobby) throws ResourceNotFoundException {
        return this.studentService.addHobby(id, hobby);
    }


    @PutMapping("/dob/{id}")
    public ResponseEntity<Object> addDobCertificate(@PathVariable Integer id, @RequestParam(value = "certificateImage",required = false) MultipartFile certificateImage) throws ResourceNotFoundException, IOException {
        return this.studentService.addDobCertificate(id, certificateImage);
    }

    @GetMapping("/class/{classId}")
    public ResponseEntity<List<StudentResponseDto>> getStudentsByClassId(@PathVariable Integer classId, @RequestParam(name = "pageno") int pageNo) throws ResourceNotFoundException {
        System.out.println(":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::"+pageNo);
        return this.studentService.getStudentsByClassId(classId, pageNo);
    }

    @GetMapping("/byUserId/{userId}")
    public ResponseEntity<Integer> getStudentByUserId(@PathVariable Integer userId) throws ResourceNotFoundException{
        return this.studentService.getStudentDetailByUserId(userId);
    }

    @GetMapping("/byAttendance/{classId}")
    public ResponseEntity<List<StudentResponseDto>> getStudentsByAttendance(@PathVariable Integer classId) throws ResourceNotFoundException{
        return this.studentService.getStudentsByAttendance(classId);
    }
}
