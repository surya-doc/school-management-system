package com.project.SchoolManagementSystem.students.Dao;

import com.project.SchoolManagementSystem.classes.Model.ClassEntity;
import com.project.SchoolManagementSystem.students.Model.StudentEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentDao extends JpaRepository<StudentEntity,Integer> {
    @Override
    Page<StudentEntity> findAll(Pageable pageable);

    @Query(value = "select * from students where students.user_id=?1", nativeQuery = true)
    public StudentEntity findByUserId(Integer userId);


    @Query(value = "SELECT t1.student_id\n" +
            "FROM students t1\n" +
            "LEFT JOIN attendance t2 ON t2.student_id = t1.student_id\n" +
            "WHERE t2.student_id IS NULL AND t1.class_id=?1", nativeQuery = true)
    public List<Integer> studentByAttendance(Integer classId);

    @Query(value = "select * from students where students.class_id=?1 order by students.user_id LIMIT ?2 OFFSET ?3 ", nativeQuery = true)
    public List<StudentEntity> findByClassId(Integer classId, Integer limit, Integer offset);

    @Modifying
    @Transactional
    @Query(value = "delete from students where students.user_id=?1", nativeQuery = true)
    void deleteByUserId(Integer userId);
}
