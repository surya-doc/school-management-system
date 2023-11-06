package com.project.SchoolManagementSystem.users.Dao;

import com.project.SchoolManagementSystem.classes.Model.ClassEntity;
import com.project.SchoolManagementSystem.users.Model.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<UserEntity, Integer> {
    @Modifying
    @Transactional
    @Query(value = "delete from user where user.user_id=?1", nativeQuery = true)
    void deleteByUserId(Integer userId);

    public UserEntity findByEmail(String email);

    Page<UserEntity> findByRole(String role, Pageable pageable);
}
