package com.project.SchoolManagementSystem.attendence.Dao;

import com.project.SchoolManagementSystem.attendence.Model.AttendanceRecordEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttendanceRecordDao  extends JpaRepository<AttendanceRecordEntity,Integer> {

//    @Query(value = "SELECT student_id, " +
//            "SUM(CASE WHEN status = 0 THEN 0 ELSE 1 END) AS present_days, " +
//            "COUNT(*) AS total_days " +
//            "FROM attendance_record " +
//            "WHERE MONTH(date) = ?1 " +
//            "GROUP BY student_id", nativeQuery = true)

    @Query(value = "select s.student_id as student_id,SUM(CASE WHEN status = 0 THEN 0 ELSE 1 END) AS present_days,count(*) as total_days, s.user_id as user_id, u.full_name as name from attendance_record a inner join students s on a.student_id=s.student_id inner join user u on s.user_id = u.user_id  where month(a.date) = ?1 and s.class_id = ?2 group by a.student_id;",nativeQuery = true)
    public List<Object[]> getAllStudentsAttendanceRecordsByMonth(Integer month,Integer classId);

    @Transactional
    @Modifying
    @Query(value = "delete from attendance_record a where a.student_id =?1", nativeQuery = true)
    public void deleteAttendanceByStudentId(Integer studentId);
}
