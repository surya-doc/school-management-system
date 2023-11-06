package com.project.SchoolManagementSystem.teachers.Controller;

import com.project.SchoolManagementSystem.address.Dto.AddressMapper;
import com.project.SchoolManagementSystem.address.Service.Impl.AddressServiceImpl;
import com.project.SchoolManagementSystem.exception.ResourceNotFoundException;
import com.project.SchoolManagementSystem.students.Dto.StudentResponseDto;
import com.project.SchoolManagementSystem.teachers.Dto.*;
import com.project.SchoolManagementSystem.teachers.Service.Impl.TeacherServiceImpl;
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
@RequestMapping("/api/teacher")
@CrossOrigin("*")
public class TeacherController {
    private TeacherServiceImpl teacherService;
    private UserServiceImpl userService;
    private AddressServiceImpl addressService;
    private UserDtoMapper userDtoMapper;
    private AddressMapper addressMapper;
    private TeacherMapper teacherMapper;
    @Autowired
    public TeacherController(TeacherServiceImpl teacherService, UserServiceImpl userService, AddressServiceImpl addressService, UserDtoMapper userDtoMapper, AddressMapper addressMapper, TeacherMapper teacherMapper) {
        this.teacherService = teacherService;
        this.userService = userService;
        this.addressService = addressService;
        this.userDtoMapper = userDtoMapper;
        this.addressMapper = addressMapper;
        this.teacherMapper = teacherMapper;
    }

    @GetMapping
    public ResponseEntity<List<TeacherResponseDto>> getAllTeachers() throws ResourceNotFoundException{
        return this.teacherService.getAllTeachers();
    }

    @GetMapping("/page")
    public ResponseEntity<List<TeacherResponseDto>> getAllTeachersInPages(@RequestParam(name = "pageno") int pageNo) throws ResourceNotFoundException {
        return this.teacherService.getTeachersByPage(pageNo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherDetailsDto> getTeacherDetail(@PathVariable Integer id) throws ResourceNotFoundException, IOException {
        return this.teacherService.getTeacherById(id);
    }

    @Secured("ROLE_admin")
    @PostMapping
    public ResponseEntity<Object> createTeacher(
            @RequestParam(value = "image",required = false) MultipartFile image,
            @RequestParam("teacherData") String teacherData) throws ResourceNotFoundException, IOException {
        this.teacherService.createTeacher(image, teacherData);
        return ResponseEntity.ok(Collections.singletonMap("message", "File uploaded successfully"));
    }

    @Secured("ROLE_admin")
    @PutMapping
    public ResponseEntity<Object> updateTeacher(
            @RequestParam(value = "image",required = false) MultipartFile image,
            @RequestParam("teacherData") String teacherData) throws ResourceNotFoundException, IOException {
        this.teacherService.updateTeacher(image, teacherData);
        return ResponseEntity.ok(Collections.singletonMap("message","Teacher updated"));
    }

    @Secured("ROLE_admin")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTeacher(@PathVariable Integer id) throws ResourceNotFoundException {
        this.addressService.deleteAddressOfUser(id);
        return this.teacherService.deleteTeacher(id);
    }

    @Secured("ROLE_admin")
    @PutMapping("/assign/subject")
    public ResponseEntity<String> assignSubjectToTeacher(@RequestBody TeacherSubjectDto teacherSubjectDto) throws ResourceNotFoundException {
        return this.teacherService.assignSubjectToTeacher(teacherSubjectDto.getTeacher_id(), teacherSubjectDto.getSubject_id());
    }

    @Secured("ROLE_admin")
    @PutMapping("/assign/class")
    public ResponseEntity<String> assignClassToTeacher(@RequestBody TeacherClassDto teacherClassDto) throws ResourceNotFoundException {
        return this.teacherService.assignClassToTeacher(teacherClassDto.getTeacher_id(), teacherClassDto.getClass_id());
    }

    @GetMapping("qualification/{qualification}")
    public ResponseEntity<List<TeacherResponseDto>> getteachersByQualification(@PathVariable String qualification) throws ResourceNotFoundException{
        System.out.println("******************************"+qualification);
        return this.teacherService.getTeachersByQualification(qualification);
    }

    @GetMapping("/byUserId/{userId}")
    public ResponseEntity<Integer> getTeacherByUserId(@PathVariable Integer userId) throws ResourceNotFoundException {
        return this.teacherService.getTeacherByUserId(userId);
    }
}
