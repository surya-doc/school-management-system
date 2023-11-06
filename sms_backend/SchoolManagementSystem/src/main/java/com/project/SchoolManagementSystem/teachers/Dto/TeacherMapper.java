package com.project.SchoolManagementSystem.teachers.Dto;

import com.project.SchoolManagementSystem.classes.Model.ClassEntity;
import com.project.SchoolManagementSystem.exception.ResourceNotFoundException;
import com.project.SchoolManagementSystem.subjects.Model.SubjectEntity;
import com.project.SchoolManagementSystem.teachers.Dao.TeacherDao;
import com.project.SchoolManagementSystem.teachers.Model.TeacherEntity;
import com.project.SchoolManagementSystem.users.Dto.UserReqResDto;
import com.project.SchoolManagementSystem.users.Model.UserEntity;
import com.project.SchoolManagementSystem.users.Service.Impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Component
public class TeacherMapper {
    private UserServiceImpl userService;
    private TeacherDao teacherDao;

    @Autowired
    public TeacherMapper(UserServiceImpl userService, TeacherDao teacherDao) {
        this.userService = userService;
        this.teacherDao = teacherDao;
    }

    public TeacherDto mapToTeacherDto(TeacherEntity teacherEntity){
        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setUser_id(teacherEntity.getUser_id().getUser_id());
        teacherDto.setQualification(teacherEntity.getQualification());
        return teacherDto;
    }

    public TeacherEntity mapToTeacherEntity(TeacherDto teacherDto) throws ResourceNotFoundException {
        UserEntity user = this.userService.getUserDetailsById(teacherDto.getUser_id()).getBody();
        List<ClassEntity> classes = new ArrayList<>();
        List<SubjectEntity> subjects = new ArrayList<>();

        TeacherEntity teacher = new TeacherEntity();
        teacher.setTeacher_id(null);
        teacher.setQualification(teacherDto.getQualification());
        teacher.setUser_id(user);
        teacher.setClassEntityList(classes);
        teacher.setSubjectEntityList(subjects);

        return teacher;
    }

    public UserReqResDto mapToUserReqResDto(TeacherResponseDto teacherUpdateRequestDto){
        byte[] bytes = teacherUpdateRequestDto.getProfile_pic();
        String text = new String(bytes, StandardCharsets.UTF_8);

        UserReqResDto userReqResDto = new UserReqResDto();
        userReqResDto.setUser_id(teacherUpdateRequestDto.getUser_id());
        userReqResDto.setEmail(teacherUpdateRequestDto.getEmail());
        userReqResDto.setFull_name(teacherUpdateRequestDto.getFull_name());
        userReqResDto.setPh_number(teacherUpdateRequestDto.getPh_number());
        userReqResDto.setDob(teacherUpdateRequestDto.getDob());
        userReqResDto.setGender(teacherUpdateRequestDto.getGender());
        userReqResDto.setRole(teacherUpdateRequestDto.getRole());
        userReqResDto.setProfile_pic(text);
        return userReqResDto;
    }

    public TeacherResponseDto mapToTeacherResDto(UserReqResDto userReqResDto) throws IOException, ResourceNotFoundException {

        byte[] image = null;
        if(!userReqResDto.getProfile_pic().isEmpty())
        {
            image = Files.readAllBytes(new File(userReqResDto.getProfile_pic()).toPath());
        }
        TeacherEntity teacher = this.teacherDao.findByUserId(userReqResDto.getUser_id());
        String qualification = "";
        if(teacher != null){
            System.out.println(teacher.getQualification());
            qualification = teacher.getQualification();
        }

        TeacherResponseDto teacherResponseDto = new TeacherResponseDto();
        teacherResponseDto.setUser_id(userReqResDto.getUser_id());
        teacherResponseDto.setEmail(userReqResDto.getEmail());
        teacherResponseDto.setFull_name(userReqResDto.getFull_name());
        teacherResponseDto.setPh_number(userReqResDto.getPh_number());
        teacherResponseDto.setDob(userReqResDto.getDob());
        teacherResponseDto.setGender(userReqResDto.getGender());
        teacherResponseDto.setRole(userReqResDto.getRole());
        teacherResponseDto.setProfile_pic(image);
        teacherResponseDto.setQualification(qualification);

        return teacherResponseDto;
    }

    public TeacherDto mapToTeacherDtoFromCreateTeacherDataDto(CreateTeacherDataDto createTeacherDataDto){
        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setUser_id(createTeacherDataDto.getId());
        teacherDto.setQualification(createTeacherDataDto.getQualification());
        return teacherDto;
    }

    public TeacherResponseDto maptoTeacherResDtoFromTeacherEntity(TeacherEntity teacherEntity) throws IOException {
        byte[] image = Files.readAllBytes(new File(teacherEntity.getUser_id().getProfile_pic()).toPath());
        TeacherResponseDto teacherResponseDto = new TeacherResponseDto();
        teacherResponseDto.setUser_id(teacherEntity.getUser_id().getUser_id());
        teacherResponseDto.setEmail(teacherEntity.getUser_id().getEmail());
        teacherResponseDto.setFull_name(teacherEntity.getUser_id().getFull_name());
        teacherResponseDto.setPh_number(teacherEntity.getUser_id().getPh_number());
        teacherResponseDto.setDob(teacherEntity.getUser_id().getDob());
        teacherResponseDto.setGender(teacherEntity.getUser_id().getGender());
        teacherResponseDto.setRole(teacherEntity.getUser_id().getRole());
        teacherResponseDto.setProfile_pic(image);
        teacherResponseDto.setQualification(teacherEntity.getQualification());

        return teacherResponseDto;
    }
}
