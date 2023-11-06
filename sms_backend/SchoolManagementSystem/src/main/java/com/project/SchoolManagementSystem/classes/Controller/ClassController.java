package com.project.SchoolManagementSystem.classes.Controller;

import com.project.SchoolManagementSystem.classes.Dto.ClassDto;
import com.project.SchoolManagementSystem.classes.Dto.ClassResDto;
import com.project.SchoolManagementSystem.classes.Dto.ClassesMapper;
import com.project.SchoolManagementSystem.classes.Model.ClassEntity;
import com.project.SchoolManagementSystem.classes.Service.Impl.ClassServiceImpl;
import com.project.SchoolManagementSystem.exception.ResourceNotFoundException;
import com.project.SchoolManagementSystem.teachers.Dto.TeacherClassDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/class")
@CrossOrigin("*")
public class ClassController {
    private ClassServiceImpl classService;
    private ClassesMapper classesMapper;

    @Autowired
    public ClassController(ClassServiceImpl classService, ClassesMapper classesMapper) {
        this.classService = classService;
        this.classesMapper = classesMapper;
    }

    @GetMapping()
    public ResponseEntity<List<ClassResDto>> getAllClasses() throws ResourceNotFoundException {
        return this.classService.getAllClasses();
    }

    @GetMapping("/page")
    public ResponseEntity<List<ClassResDto>> getAllClassesInPages(@RequestParam(name = "pageno") int pageNo) throws ResourceNotFoundException {
        return this.classService.getAllClassesByPage(pageNo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassEntity> getClassById(@PathVariable Integer id) throws ResourceNotFoundException {
        return this.classService.getClassById(id);
    }

    @PostMapping
    public ResponseEntity<Object> createClass(@RequestBody ClassDto classDto) throws ResourceNotFoundException {
        this.classService.createClass(classesMapper.mapToClassEntity(classDto));
        return ResponseEntity.ok(Collections.singletonMap("message", "Class created"));
    }

    @PutMapping
    public ResponseEntity<Object> updateClass(@RequestBody ClassDto classDto) throws ResourceNotFoundException {
        this.classService.updateClass(classesMapper.mapToClassEntity(classDto));
        return ResponseEntity.ok(Collections.singletonMap("message", "Class updated"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClassById(@PathVariable Integer id) throws ResourceNotFoundException {
        return this.classService.deleteClass(id);
    }

    @GetMapping("qualification/{qualification}")
    public ResponseEntity<List<ClassResDto>> getALlClassesByQualification(@PathVariable String qualification) throws ResourceNotFoundException{
        return this.classService.getClassesByQualification(qualification);
    }

    @Secured("ROLE_admin")
    @PutMapping("/assign/teacher")
    public ResponseEntity<Object> assignClassteacher(@RequestBody TeacherClassDto teacherClassDto) throws ResourceNotFoundException, IOException {
        return this.classService.assignClassTeacher(teacherClassDto.getClass_id(), teacherClassDto.getTeacher_id());
    }

    @GetMapping("/byClassTeacher/{teacher_id}")
    public ResponseEntity<Integer> getClassByClassTeacher(@PathVariable Integer teacher_id) throws ResourceNotFoundException{
        return this.classService.getClassByClassTeacherId(teacher_id);
    }
}
