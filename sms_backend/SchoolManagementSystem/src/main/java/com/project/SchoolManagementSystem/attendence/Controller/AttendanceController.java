package com.project.SchoolManagementSystem.attendence.Controller;


import com.project.SchoolManagementSystem.attendence.Dto.AttendanceRecordDto;
import com.project.SchoolManagementSystem.attendence.Model.AttendanceEntity;
import com.project.SchoolManagementSystem.attendence.Service.Impl.AttendanceServiceImpl;
import com.project.SchoolManagementSystem.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RequestMapping("/attendance")
@RestController
public class AttendanceController {
    private AttendanceServiceImpl attendanceService;

    @Autowired
    public AttendanceController(AttendanceServiceImpl attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping("/{student_id}")
    public ResponseEntity<String> addUserAttendance(@PathVariable Integer student_id, @RequestBody AttendanceEntity attendance) throws ResourceNotFoundException {
        return this.attendanceService.createStudentAttendence(student_id,attendance);
    }

    @GetMapping("/allRecords/{month}/{classId}")
    public ResponseEntity<List<AttendanceRecordDto>> getAllStudentsAttendanceRecordsByMonth(@PathVariable Integer month, @PathVariable Integer classId) throws  ResourceNotFoundException{
        return this.attendanceService.getAllStudentsAttendanceRecordsByMonth(month,classId);
    }

}
