package com.project.SchoolManagementSystem.subjects.Controller;

import com.project.SchoolManagementSystem.exception.ResourceNotFoundException;
import com.project.SchoolManagementSystem.subjects.Dto.SubjectDto;
import com.project.SchoolManagementSystem.subjects.Dto.SubjectMapper;
import com.project.SchoolManagementSystem.subjects.Model.SubjectEntity;
import com.project.SchoolManagementSystem.subjects.Service.Impl.SubjectServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subject")
@CrossOrigin("*")
public class SubjectController {

    private SubjectServiceImpl subjectService;
    private SubjectMapper subjectMapper;

    @Autowired
    public SubjectController(SubjectServiceImpl subjectService, SubjectMapper subjectMapper) {
        this.subjectService = subjectService;
        this.subjectMapper = subjectMapper;
    }

    @GetMapping
    public ResponseEntity<List<SubjectEntity>> getAllSubjects() throws ResourceNotFoundException {
        return this.subjectService.getAllSubjects();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubjectEntity> getSubjectById(@PathVariable Integer id) throws ResourceNotFoundException {
        return this.subjectService.getSubjectById(id);
    }

    @Secured("ROLE_admin")
    @PostMapping
    public ResponseEntity<String> createSubject(@RequestBody SubjectDto subjectDto) throws ResourceNotFoundException {
        SubjectEntity subjectEntity = this.subjectMapper.mapToSubjectEntity(subjectDto);
        return this.subjectService.createSubject(subjectEntity);
    }

    @Secured("ROLE_admin")
    @PutMapping
    public ResponseEntity<String> updateSubject(@RequestBody SubjectDto subjectDto) throws ResourceNotFoundException {
        return this.subjectService.updateSubject(this.subjectMapper.mapToSubjectEntity(subjectDto));
    }

    @Secured("ROLE_admin")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSubjectById(@PathVariable Integer id) throws ResourceNotFoundException {
        return this.subjectService.deleteSubject(id);
    }
}
