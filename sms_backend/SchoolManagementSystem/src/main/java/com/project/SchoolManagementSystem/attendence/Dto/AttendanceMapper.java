package com.project.SchoolManagementSystem.attendence.Dto;

import com.project.SchoolManagementSystem.attendence.Model.AttendanceEntity;
import com.project.SchoolManagementSystem.attendence.Model.AttendanceRecordEntity;
import com.project.SchoolManagementSystem.students.Dao.StudentDao;
import com.project.SchoolManagementSystem.students.Model.StudentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AttendanceMapper {
    private StudentDao studentDao;

    @Autowired
    public AttendanceMapper(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    public AttendanceRecordEntity convertToRecord(AttendanceEntity attendance){
        AttendanceRecordEntity attendanceRecord = new AttendanceRecordEntity();
        attendanceRecord.setId(null);
        attendanceRecord.setDate(attendance.getDate());
        attendanceRecord.setStatus(attendance.getStatus());
        StudentEntity student = this.studentDao.findById(attendance.getStudent_id()).orElseThrow();
        attendanceRecord.setStudents(student);
        return attendanceRecord;
    }
}
