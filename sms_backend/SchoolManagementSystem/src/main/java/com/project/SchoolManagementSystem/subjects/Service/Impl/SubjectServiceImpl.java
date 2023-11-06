package com.project.SchoolManagementSystem.subjects.Service.Impl;

import com.project.SchoolManagementSystem.exception.ResourceNotFoundException;
import com.project.SchoolManagementSystem.subjects.Dao.SubjectDao;
import com.project.SchoolManagementSystem.subjects.Model.SubjectEntity;
import com.project.SchoolManagementSystem.subjects.Service.SubjectService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubjectServiceImpl implements SubjectService {

    private SubjectDao subjectDao;

    @Autowired
    public SubjectServiceImpl(SubjectDao subjectDao) {
        this.subjectDao = subjectDao;
    }

    @Override
    public ResponseEntity<List<SubjectEntity>> getAllSubjects() throws ResourceNotFoundException {
        List<SubjectEntity> subjects = this.subjectDao.findAll();
        return ResponseEntity.ok(subjects);
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public ResponseEntity<String> createSubject(SubjectEntity subjectEntity) throws ResourceNotFoundException {
        Integer subjectId = subjectEntity.getSubject_id();
        Optional<SubjectEntity> subject =this.subjectDao.findById(subjectId);
        if (subject.isPresent()){
            throw  new ResourceNotFoundException("Subject already present with this id " + subjectId);
        }
        this.subjectDao.save(subjectEntity);
        return ResponseEntity.ok("Subject created");
    }

    @Override
    public ResponseEntity<SubjectEntity> getSubjectById(Integer subjectId) throws ResourceNotFoundException {
        System.out.println(subjectId);
        SubjectEntity subject = this.subjectDao.findBySubjectId(subjectId);
        System.out.println(subject);
        return ResponseEntity.ok(subject);
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public ResponseEntity<String> updateSubject(SubjectEntity subjectEntity) throws ResourceNotFoundException {
        Integer subjectId = subjectEntity.getSubject_id();
        Optional<SubjectEntity> subjectDetails =this.subjectDao.findById(subjectId);
        if (subjectDetails.isEmpty()){
            throw  new ResourceNotFoundException("No subject found with this id " + subjectId);
        }
        this.subjectDao.save(subjectEntity);
        return ResponseEntity.ok("Subject updated");
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public ResponseEntity<String> deleteSubject(Integer subjectId) throws ResourceNotFoundException {
        Optional<SubjectEntity> subject =this.subjectDao.findById(subjectId);
        if (subject.isEmpty()){
            throw  new ResourceNotFoundException("No subject found with this id" + subjectId);
        }
        this.subjectDao.deleteById(subjectId);
        return ResponseEntity.ok("Subject deleted");
    }
}
