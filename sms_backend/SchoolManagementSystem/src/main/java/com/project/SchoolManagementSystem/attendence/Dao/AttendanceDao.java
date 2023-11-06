package com.project.SchoolManagementSystem.attendence.Dao;

import com.project.SchoolManagementSystem.attendence.Model.AttendanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceDao extends JpaRepository<AttendanceEntity, Integer> {
}
