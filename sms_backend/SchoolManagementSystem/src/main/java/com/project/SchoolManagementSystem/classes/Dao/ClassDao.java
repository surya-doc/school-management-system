package com.project.SchoolManagementSystem.classes.Dao;

import com.project.SchoolManagementSystem.classes.Model.ClassEntity;
import com.project.SchoolManagementSystem.notice.Model.NoticeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClassDao extends JpaRepository<ClassEntity, Integer> {
    @Query(value = "select * from class where class.teacher_id=?1", nativeQuery = true)
    public ClassEntity getClassByTeacherId(Integer teacherId);

    @Override
    Page<ClassEntity> findAll(Pageable pageable);

}
