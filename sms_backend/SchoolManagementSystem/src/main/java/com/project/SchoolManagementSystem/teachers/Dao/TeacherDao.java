package com.project.SchoolManagementSystem.teachers.Dao;

import com.project.SchoolManagementSystem.teachers.Model.TeacherEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherDao extends JpaRepository<TeacherEntity, Integer> {

    @Query(value = "select * from teacher where teacher.user_id=?1", nativeQuery = true)
    public TeacherEntity findByUserId(Integer userId);

    @Modifying
    @Transactional
    @Query(value = "delete from teacher where teacher.user_id=?1", nativeQuery = true)
    void deleteByUserId(Integer userId);

    @Query(value = "SELECT * FROM teacher WHERE teacher.user_id NOT IN (SELECT teacher_id FROM class WHERE teacher_id IS NOT NULL)",
            nativeQuery = true)
    List<TeacherEntity> findTeachersNotInClass();
}
