package com.project.SchoolManagementSystem.attendence.Service;

import com.project.SchoolManagementSystem.attendence.Dto.AttendanceRecordDto;
import com.project.SchoolManagementSystem.attendence.Model.AttendanceEntity;
import com.project.SchoolManagementSystem.exception.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AttendanceService {
    public ResponseEntity<String> createStudentAttendence(Integer student_id, AttendanceEntity attendance) throws ResourceNotFoundException;
    public void addAttendanceToRecords();
    public ResponseEntity<List<AttendanceRecordDto>> getAllStudentsAttendanceRecordsByMonth(Integer month, Integer classId) throws ResourceNotFoundException;
    public void deleteAttendance(Integer studentId) throws ResourceNotFoundException;
}
