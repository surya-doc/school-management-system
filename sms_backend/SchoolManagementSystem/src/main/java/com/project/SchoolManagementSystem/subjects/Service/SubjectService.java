package com.project.SchoolManagementSystem.subjects.Service;

import com.project.SchoolManagementSystem.exception.ResourceNotFoundException;
import com.project.SchoolManagementSystem.subjects.Model.SubjectEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

public interface SubjectService {

    ResponseEntity<List<SubjectEntity>> getAllSubjects() throws ResourceNotFoundException;
    ResponseEntity<String> createSubject(SubjectEntity subjectEntity) throws ResourceNotFoundException;

    ResponseEntity<SubjectEntity> getSubjectById(Integer subjectId) throws ResourceNotFoundException;

    ResponseEntity<String> updateSubject(SubjectEntity subjectEntity) throws ResourceNotFoundException;

    ResponseEntity<String> deleteSubject(Integer subjectId) throws ResourceNotFoundException;
}
