package com.project.SchoolManagementSystem.classes.Dto;

import com.project.SchoolManagementSystem.classes.Dao.ClassDao;
import com.project.SchoolManagementSystem.classes.Model.ClassEntity;
import com.project.SchoolManagementSystem.exception.ResourceNotFoundException;
import com.project.SchoolManagementSystem.students.Model.StudentEntity;
import com.project.SchoolManagementSystem.teachers.Dto.TeacherDetailsDto;
import com.project.SchoolManagementSystem.teachers.Model.TeacherEntity;
import com.project.SchoolManagementSystem.teachers.Service.Impl.TeacherServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ClassesMapper {

    private ClassDao classDao;
    private TeacherServiceImpl teacherService;

    @Autowired
    public ClassesMapper(ClassDao classDao, TeacherServiceImpl teacherService) {
        this.classDao = classDao;
        this.teacherService = teacherService;
    }

    public ClassEntity mapToClassEntity(ClassDto classDto) throws ResourceNotFoundException {
        Optional<ClassEntity> classDetails = this.classDao.findById(classDto.getClass_id());
        List<TeacherEntity> teachers;
        List<StudentEntity> students;
        if (classDetails.isEmpty()){
            teachers = new ArrayList<>();
            students = new ArrayList<>();
        }
        else {
            teachers = classDetails.get().getTeacherEntityList();
            students = classDetails.get().getStudents();
        }

        ClassEntity classEntity = new ClassEntity();
        classEntity.setClass_id(classDto.getClass_id());
        classEntity.setClass_name(classDto.getClass_name());
        classEntity.setMin_qualification(classDto.getMin_qualification());
        classEntity.setTeacher_id(classDto.getTeacher_id());
        classEntity.setTeacherEntityList(teachers);
        classEntity.setStudents(students);
        return classEntity;
    }

    public ClassDto mapToClassDto(ClassEntity classEntity){
        ClassDto classDto = new ClassDto();
        classDto.setClass_id(classEntity.getClass_id());
        classDto.setClass_name(classEntity.getClass_name());
        classDto.setMin_qualification(classEntity.getMin_qualification());
        classDto.setTeacher_id(classEntity.getTeacher_id());
        return classDto;
    }

    public ClassResDto mapToClassResDto(ClassEntity classEntity) throws IOException, ResourceNotFoundException {
        System.out.println("****************************"+classEntity.getTeacher_id());
        TeacherDetailsDto teacherDetailsDto = null;
        if(classEntity.getTeacher_id() != null){
            teacherDetailsDto = this.teacherService.getTeacherById(classEntity.getTeacher_id()).getBody();
        }
        ClassResDto classResDto = new ClassResDto();
        classResDto.setClass_id(classEntity.getClass_id());
        classResDto.setClass_name(classEntity.getClass_name());
        classResDto.setMin_qualification(classEntity.getMin_qualification());
        classResDto.setTeacher_name(classEntity.getTeacher_id() != null ? teacherDetailsDto.getFullName() : null);
        classResDto.setTotal_students(classEntity.getStudents().size());
        return classResDto;
    }
}
