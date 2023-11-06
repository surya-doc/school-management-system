package com.project.SchoolManagementSystem.subjects.Dto;

import com.project.SchoolManagementSystem.exception.ResourceNotFoundException;
import com.project.SchoolManagementSystem.subjects.Dao.SubjectDao;
import com.project.SchoolManagementSystem.subjects.Model.SubjectEntity;
import com.project.SchoolManagementSystem.subjects.Service.Impl.SubjectServiceImpl;
import com.project.SchoolManagementSystem.teachers.Model.TeacherEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class SubjectMapper {
    private SubjectServiceImpl subjectService;
    private SubjectDao subjectDao;

    public SubjectMapper(SubjectServiceImpl subjectService, SubjectDao subjectDao) {
        this.subjectService = subjectService;
        this.subjectDao = subjectDao;
    }

    public SubjectEntity mapToSubjectEntity(SubjectDto subjectDto) throws ResourceNotFoundException {
        Optional<SubjectEntity> subject = this.subjectDao.findById(subjectDto.getSubject_id());
        List<TeacherEntity> teacherEntityList;
        if(subject.isEmpty()){
            teacherEntityList = new ArrayList<>();
        }
        else {
            teacherEntityList = subject.get().getTeacherEntityList();
        }

        SubjectEntity subjectEntity = new SubjectEntity();
        subjectEntity.setSubject_id(subjectDto.getSubject_id());
        subjectEntity.setSubject_name(subjectDto.getSubject_name());
        subjectEntity.setTeacherEntityList(teacherEntityList);
        return subjectEntity;
    }

    public SubjectDto mapToSubjectDto(SubjectEntity subjectEntity){
        SubjectDto subjectDto = new SubjectDto();
        subjectDto.setSubject_id(subjectEntity.getSubject_id());
        subjectDto.setSubject_name(subjectEntity.getSubject_name());
        return subjectDto;
    }
}
