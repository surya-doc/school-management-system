package com.project.SchoolManagementSystem.attendence.Service.Impl;

import com.project.SchoolManagementSystem.attendence.Dao.AttendanceDao;
import com.project.SchoolManagementSystem.attendence.Dao.AttendanceRecordDao;
import com.project.SchoolManagementSystem.attendence.Dto.AttendanceMapper;
import com.project.SchoolManagementSystem.attendence.Dto.AttendanceRecordDto;
import com.project.SchoolManagementSystem.attendence.Model.AttendanceEntity;
import com.project.SchoolManagementSystem.attendence.Model.AttendanceRecordEntity;
import com.project.SchoolManagementSystem.attendence.Service.AttendanceService;
import com.project.SchoolManagementSystem.exception.ResourceNotFoundException;
import com.project.SchoolManagementSystem.students.Dao.StudentDao;
import com.project.SchoolManagementSystem.students.Model.StudentEntity;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class AttendanceServiceImpl implements AttendanceService {
    private AttendanceDao attendanceDao;
    private StudentDao studentDao;
    private JdbcTemplate jdbcTemplate;
    private AttendanceRecordDao attendanceRecordDao;
    private AttendanceMapper attendanceMapper;

    @Autowired
    public AttendanceServiceImpl(JdbcTemplate jdbcTemplate, AttendanceDao attendanceDao, StudentDao studentDao, AttendanceRecordDao attendanceRecordDao, AttendanceMapper attendanceMapper) {
        this.attendanceDao = attendanceDao;
        this.studentDao = studentDao;
        this.jdbcTemplate = jdbcTemplate;
        this.attendanceRecordDao = attendanceRecordDao;
        this.attendanceMapper = attendanceMapper;
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public ResponseEntity<String> createStudentAttendence(Integer user_id, AttendanceEntity attendance) throws ResourceNotFoundException {
        StudentEntity student = this.studentDao.findByUserId(user_id);
        Date date = Calendar.getInstance().getTime();
        attendance.setDate(date);
        attendance.setStudent_id(student.getStudent_id());
        System.out.println(attendance);
        this.attendanceDao.save(attendance);
        return ResponseEntity.ok("true");
    }


    @Scheduled(cron = "0 * * * * ?")
    @Override
    public void addAttendanceToRecords() {
        System.out.println("cron executed");
        List<AttendanceEntity> attendanceEntityList = this.attendanceDao.findAll();
        List<AttendanceRecordEntity> attendanceRecordEntities = attendanceEntityList.stream().map(e->this.attendanceMapper.convertToRecord(e)).toList();
        for(AttendanceRecordEntity attendanceRecord:attendanceRecordEntities)
        {
            this.attendanceRecordDao.save(attendanceRecord);
        }
        String deleteQuery = "DELETE FROM attendance";
        jdbcTemplate.update(deleteQuery);
    }

    @Override
    public ResponseEntity<List<AttendanceRecordDto>> getAllStudentsAttendanceRecordsByMonth(Integer month, Integer classId) throws ResourceNotFoundException {
        List<Object[]> attendanceRecords = this.attendanceRecordDao.getAllStudentsAttendanceRecordsByMonth(month,classId);
        List<AttendanceRecordDto> attendanceRecordDtoList = new ArrayList<>();
        for(Object[] attendanceRecord: attendanceRecords)
        {
            System.out.println(Arrays.toString(attendanceRecord));
            AttendanceRecordDto attendanceRecordDto = new AttendanceRecordDto();
//            System.out.println((Integer) attendanceRecord[0]);
//            System.out.println((BigDecimal) attendanceRecord[1]);
//            System.out.println((Long) attendanceRecord[2]);
            attendanceRecordDto.setStudent_id((Integer) attendanceRecord[0]);
            attendanceRecordDto.setPresent_days((BigDecimal) attendanceRecord[1]);
            attendanceRecordDto.setTotal_days((Long) attendanceRecord[2]);
            attendanceRecordDto.setUser_id((Integer) attendanceRecord[3]);
            attendanceRecordDto.setName((String) attendanceRecord[4]);
            attendanceRecordDtoList.add(attendanceRecordDto);

        }
        return ResponseEntity.ok(attendanceRecordDtoList);
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public void deleteAttendance(Integer studentId) throws ResourceNotFoundException {
        this.attendanceRecordDao.deleteAttendanceByStudentId(studentId);
    }
}
