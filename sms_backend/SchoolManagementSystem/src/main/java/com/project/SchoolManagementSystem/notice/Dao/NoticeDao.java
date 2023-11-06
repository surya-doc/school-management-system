package com.project.SchoolManagementSystem.notice.Dao;

import com.project.SchoolManagementSystem.notice.Model.NoticeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeDao extends JpaRepository<NoticeEntity, Integer> {
    @Override
    Page<NoticeEntity> findAll(Pageable pageable);
}
