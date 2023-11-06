package com.project.SchoolManagementSystem.classes.Service.Impl;

import com.project.SchoolManagementSystem.classes.Dao.ClassDao;
import com.project.SchoolManagementSystem.classes.Dto.ClassResDto;
import com.project.SchoolManagementSystem.classes.Dto.ClassesMapper;
import com.project.SchoolManagementSystem.classes.Model.ClassEntity;
import com.project.SchoolManagementSystem.classes.Service.ClassService;
import com.project.SchoolManagementSystem.exception.ResourceNotFoundException;
import com.project.SchoolManagementSystem.teachers.Dto.TeacherDetailsDto;
import com.project.SchoolManagementSystem.teachers.Service.Impl.TeacherServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ClassServiceImpl implements ClassService {
    
    private ClassDao classDao;
    private ClassesMapper classesMapper;
    private TeacherServiceImpl teacherService;

    @Autowired
    public ClassServiceImpl(ClassDao classDao, ClassesMapper classesMapper, TeacherServiceImpl teacherService) {
        this.classDao = classDao;
        this.classesMapper = classesMapper;
        this.teacherService = teacherService;
    }

    @Override
    public ResponseEntity<List<ClassResDto>> getAllClasses() throws ResourceNotFoundException {
        List<ClassEntity> classes = this.classDao.findAll(Pageable.unpaged()).getContent();
        List<ClassResDto> updatedClasses = classes.stream().map((e) -> {
            try {
                return this.classesMapper.mapToClassResDto(e);
            } catch (IOException | ResourceNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        }).toList();
        System.out.println(updatedClasses);
        return ResponseEntity.ok(updatedClasses);
    }

    @Override
    public ResponseEntity<List<ClassResDto>> getAllClassesByPage(Integer pageNo) throws ResourceNotFoundException {
        Pageable pageRequest = PageRequest.of(pageNo, 5);
        List<ClassEntity> classes = this.classDao.findAll(pageRequest).getContent();
        List<ClassResDto> updatedClasses = classes.stream().map((e) -> {
            try {
                return this.classesMapper.mapToClassResDto(e);
            } catch (IOException | ResourceNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        }).toList();
        System.out.println(updatedClasses);
        return ResponseEntity.ok(updatedClasses);
    }

    @Override
    public ResponseEntity<ClassEntity> getClassById(Integer classId) throws ResourceNotFoundException {
        ClassEntity classEntity = this.classDao.findById(classId).orElseThrow(() -> new ResourceAccessException("No Class found with this id " + classId));
        return ResponseEntity.ok(classEntity);
    }

    @Override
    public ResponseEntity<String> createClass(ClassEntity classEntity) throws ResourceNotFoundException {
        Integer classId = classEntity.getClass_id();
        Optional<ClassEntity> classDetails = this.classDao.findById(classId);
        if (classDetails.isPresent()){
            throw new ResourceNotFoundException("Class already present with this id " + classId);
        }
        this.classDao.save(classEntity);
        return ResponseEntity.ok("Class created");
    }

    @Override
    public ResponseEntity<String> updateClass(ClassEntity classEntity) throws ResourceNotFoundException {
        Integer classId = classEntity.getClass_id();
        Optional<ClassEntity> classDetail = this.classDao.findById(classId);
        if (classDetail.isEmpty()){
            throw new ResourceNotFoundException("No class found with this id " + classId);
        }
        this.classDao.save(classEntity);
        return ResponseEntity.ok("Class updated");
    }

    @Override
    public ResponseEntity<String> deleteClass(Integer classId) throws ResourceNotFoundException {
        Optional<ClassEntity> classDetail = this.classDao.findById(classId);
        if (classDetail.isEmpty()){
            throw new ResourceNotFoundException("No class found with this id " + classId);
        }
        this.classDao.deleteById(classId);
        return ResponseEntity.ok("Class deleted");
    }

    @Override
    public ResponseEntity<Object> assignClassTeacher(Integer classId, Integer teacherId) throws ResourceNotFoundException, IOException {
        TeacherDetailsDto teacher = this.teacherService.getTeacherById(teacherId).getBody();
        if(teacher == null){
            throw new ResourceNotFoundException("Teacher not found with this id."+teacherId);
        }
        Optional<ClassEntity> optionalClassEntity = this.classDao.findById(classId);
        if(optionalClassEntity.isPresent()){
            ClassEntity classEntity = optionalClassEntity.get();
            classEntity.setTeacher_id(teacherId);
            this.classDao.save(classEntity);
        }
        else{
            throw new ResourceNotFoundException("Class not found with this id."+classId);
        }
        return ResponseEntity.ok(Collections.singletonMap("message", "Class teacher successfully assigned."));
    }

    @Override
    public ResponseEntity<List<ClassResDto>> getClassesByQualification(String qualification){
        List<ClassEntity> classes = this.classDao.findAll();
        System.out.println(classes);
        Integer teacherQualification = getQualificationIndex(qualification);
        List<ClassResDto> updatedClasses = classes.stream()
                .filter((e) -> getQualificationIndex(e.getMin_qualification()) <= teacherQualification)
                .map((e) -> {
                    try {
                        return this.classesMapper.mapToClassResDto(e);
                    } catch (IOException | ResourceNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                })
                .toList();
        System.out.println(updatedClasses);
        return ResponseEntity.ok(updatedClasses);
    }

    public Integer getQualificationIndex(String qualification){
        Integer index = switch (qualification) {
            case "B.Sc" -> 1;
            case "M.Sc" -> 2;
            case "PhD" -> 3;
            default -> 1;
        };
        return index;
    }

    @Override
    public ResponseEntity<Integer> getClassByClassTeacherId(Integer teacher_id) throws ResourceNotFoundException {
        ClassEntity classEntity = this.classDao.getClassByTeacherId(teacher_id);
        if(classEntity==null)
        {
            throw new ResourceNotFoundException("Not a class teacher of any class");
        }
        return ResponseEntity.ok(classEntity.getClass_id());
    }
    
//    public boolean isTeacherAssigned(Intege teacherId)
}
