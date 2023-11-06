package com.project.SchoolManagementSystem.subjects.Dao;

import com.project.SchoolManagementSystem.subjects.Model.SubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SubjectDao extends JpaRepository<SubjectEntity, Integer> {
    @Query(value = "SELECT * FROM school_management_db.subject where subject_id=?1", nativeQuery = true)
    public SubjectEntity findBySubjectId(Integer subject_id);
}
