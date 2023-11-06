package com.project.SchoolManagementSystem.students.Dto;

import com.project.SchoolManagementSystem.attendence.Model.AttendanceRecordEntity;
import com.project.SchoolManagementSystem.classes.Model.ClassEntity;
import com.project.SchoolManagementSystem.classes.Service.Impl.ClassServiceImpl;
import com.project.SchoolManagementSystem.exception.ResourceNotFoundException;

import com.project.SchoolManagementSystem.hobbies.Model.HobbyEntity;
import com.project.SchoolManagementSystem.students.Model.StudentEntity;
import com.project.SchoolManagementSystem.users.Dto.UserReqResDto;
import com.project.SchoolManagementSystem.users.Model.UserEntity;
import com.project.SchoolManagementSystem.users.Service.Impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Component
public class StudentMapper {
    private UserServiceImpl userService;
    private ClassServiceImpl classService;

    @Autowired
    public StudentMapper(UserServiceImpl userService, ClassServiceImpl classService) {
        this.userService = userService;
        this.classService = classService;
    }

    public StudentDto covertToDto(StudentEntity student)
    {
        StudentDto studentDto = new StudentDto();
        studentDto.setStudent_id(student.getStudent_id());
//        studentDto.setDob_certificate(student.getDob_certificate());
        studentDto.setClass_id(student.getStudent_class());
        studentDto.setHobbbies(student.getStudent_hobbies());
        studentDto.setAttendence(student.getStudent_attendance());
        studentDto.setUser_id(student.getUser_id().getUser_id());
        return studentDto;
    }

    public StudentEntity convertToEntity(StudentDto student) throws ResourceNotFoundException {
        UserEntity user = this.userService.getUserDetailsById(student.getUser_id()).getBody();
//        byte[] image = Files.readAllBytes(new File(userReqResDto.getProfile_pic()).toPath());
        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setStudent_id(student.getStudent_id());
//        studentEntity.setDob_certificate();
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%"+student.getClass_id());
        studentEntity.setStudent_class(student.getClass_id());
        studentEntity.setStudent_hobbies(student.getHobbbies());
        studentEntity.setStudent_attendance(student.getAttendence());
        studentEntity.setUser_id(user);
        return studentEntity;
    }

    public StudentDto mapToStudentDtoFromCreateStudentDataDto(CreateStudentDataDto createStudentDataDto) throws ResourceNotFoundException {
        byte[] dobCertificate = new byte[0];
        ClassEntity classEntity = this.classService.getClassById(createStudentDataDto.getClassId()).getBody();
        List<HobbyEntity> hobbyEntities = new ArrayList<>();
        List<AttendanceRecordEntity> attendanceEntities = new ArrayList<>();

        StudentDto studentDto = new StudentDto();
        studentDto.setStudent_id(null);
        studentDto.setDob_certificate(dobCertificate);
        studentDto.setClass_id(classEntity);
        studentDto.setHobbbies(hobbyEntities);
        studentDto.setAttendence(attendanceEntities);
        studentDto.setUser_id(createStudentDataDto.getId());
        return studentDto;
    }

    public StudentResponseDto mapToStudentResDto(UserReqResDto userReqResDto) throws IOException {
        byte[] image = null;
        if(!userReqResDto.getProfile_pic().isEmpty())
        {
            image = Files.readAllBytes(new File(userReqResDto.getProfile_pic()).toPath());
        }
        StudentResponseDto studentResponseDto = new StudentResponseDto();
        studentResponseDto.setUser_id(userReqResDto.getUser_id());
        studentResponseDto.setEmail(userReqResDto.getEmail());
        studentResponseDto.setFull_name(userReqResDto.getFull_name());
        studentResponseDto.setPh_number(userReqResDto.getPh_number());
        studentResponseDto.setDob(userReqResDto.getDob());
        studentResponseDto.setGender(userReqResDto.getGender());
        studentResponseDto.setRole(userReqResDto.getRole());
        studentResponseDto.setProfile_pic(image);
        return studentResponseDto;
    }
}
