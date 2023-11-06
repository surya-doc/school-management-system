package com.project.SchoolManagementSystem.users.Dto;

import com.project.SchoolManagementSystem.address.Model.AddressEntity;
import com.project.SchoolManagementSystem.students.Dto.CreateStudentDataDto;
import com.project.SchoolManagementSystem.teachers.Dto.CreateTeacherDataDto;
import com.project.SchoolManagementSystem.users.Model.UserEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class UserDtoMapper {

    public UserDto mapToUserDto(UserEntity userEntity) {
        UserDto userDto = new UserDto();

        userDto.setUser_id(userEntity.getUser_id());
        userDto.setEmail(userEntity.getEmail());
        userDto.setPassword(userEntity.getPassword());
        userDto.setFull_name(userEntity.getFull_name());
        userDto.setPh_number(userEntity.getPh_number());
        userDto.setDob(userEntity.getDob());
        userDto.setGender(userEntity.getGender());
        userDto.setRole(userEntity.getRole());
        userDto.setProfile_pic(userEntity.getProfile_pic());

        return userDto;
    }

    public UserEntity mapToUserEntity(UserDto userDto) {
        List<AddressEntity> address = new ArrayList<>();

        UserEntity userEntity = new UserEntity();
        userEntity.setUser_id(userDto.getUser_id());
        userEntity.setEmail(userDto.getEmail());
        userEntity.setPassword(userDto.getPassword());
        userEntity.setFull_name(userDto.getFull_name());
        userEntity.setPh_number(userDto.getPh_number());
        userEntity.setDob(userDto.getDob());
        userEntity.setGender(userDto.getGender());
        userEntity.setProfile_pic(userDto.getProfile_pic());
        userEntity.setRole(userDto.getRole());
        userEntity.setUser_address(address);
        return userEntity;
    }

    public UserReqResDto mapToUserResponseDto(UserEntity userEntity){
        UserReqResDto userReqResDto = new UserReqResDto();
        System.out.println("56 userEntity"+userEntity);
        userReqResDto.setUser_id(userEntity.getUser_id());
        userReqResDto.setEmail(userEntity.getEmail());
        userReqResDto.setFull_name(userEntity.getFull_name());
        userReqResDto.setPh_number(userEntity.getPh_number());
        userReqResDto.setDob(userEntity.getDob());
        userReqResDto.setGender(userEntity.getGender());
        userReqResDto.setRole(userEntity.getRole());
        userReqResDto.setProfile_pic(userEntity.getProfile_pic());

        return userReqResDto;
    }

    public UserDto mapToUserDtoFromCreateTeacherDataDto(CreateTeacherDataDto createTeacherDataDto, String password, String profilePic){
        String dateString = createTeacherDataDto.getDob(); // Replace with your date string
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate localDate = LocalDate.parse(dateString, formatter);
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        UserDto userDto = new UserDto();
        userDto.setUser_id(createTeacherDataDto.getId());
        userDto.setEmail(createTeacherDataDto.getEmail());
        userDto.setPassword(password);
        userDto.setFull_name(createTeacherDataDto.getFullName());
        userDto.setPh_number(createTeacherDataDto.getPhoneNumber());
        userDto.setDob(date);
        userDto.setGender(createTeacherDataDto.getGender());
        userDto.setRole("teacher");
        userDto.setProfile_pic(profilePic);

        return userDto;
    }

    public UserDto mapToUserDtoFromCreateStudentDataDto(CreateStudentDataDto createStudentDataDto, String password){
        String dateString = createStudentDataDto.getDob(); // Replace with your date string
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate localDate = LocalDate.parse(dateString, formatter);
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        UserDto userDto = new UserDto();

        userDto.setUser_id(createStudentDataDto.getId());
        userDto.setEmail(createStudentDataDto.getEmail());
        userDto.setPassword(password);
        userDto.setFull_name(createStudentDataDto.getFullName());
        userDto.setPh_number(createStudentDataDto.getPhoneNumber());
        userDto.setDob(date);
        userDto.setGender(createStudentDataDto.getGender());
        userDto.setRole("student");
        userDto.setProfile_pic(createStudentDataDto.getProfilePic());

        return userDto;
    }

    public UserReqResDto mapToUserReqResDtoFromCreateTeacherDataDto(CreateTeacherDataDto createTeacherDataDto, String profilePic){
        String dateString = createTeacherDataDto.getDob(); // Replace with your date string
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(dateString, formatter);
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        UserReqResDto userReqResDto = new UserReqResDto();
        userReqResDto.setUser_id(createTeacherDataDto.getId());
        userReqResDto.setEmail(createTeacherDataDto.getEmail());
        userReqResDto.setFull_name(createTeacherDataDto.getFullName());
        userReqResDto.setPh_number(createTeacherDataDto.getPhoneNumber());
        userReqResDto.setDob(date);
        userReqResDto.setGender(createTeacherDataDto.getGender());
        userReqResDto.setRole("teacher");
        userReqResDto.setProfile_pic(profilePic);

        return userReqResDto;
    }

    public UserReqResDto mapToUserReqResDtoFromCreateStudentDataDto(CreateStudentDataDto createStudentDataDto, String profilePic){
        String dateString = createStudentDataDto.getDob(); // Replace with your date string
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(dateString, formatter);
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        UserReqResDto userReqResDto = new UserReqResDto();
        userReqResDto.setUser_id(createStudentDataDto.getId());
        userReqResDto.setEmail(createStudentDataDto.getEmail());
        userReqResDto.setFull_name(createStudentDataDto.getFullName());
        userReqResDto.setPh_number(createStudentDataDto.getPhoneNumber());
        userReqResDto.setDob(date);
        userReqResDto.setGender(createStudentDataDto.getGender());
        userReqResDto.setRole("student");
        userReqResDto.setProfile_pic(profilePic);
        return userReqResDto;
    }
}
