package com.project.SchoolManagementSystem.resetPassword.Dao;

import com.project.SchoolManagementSystem.resetPassword.Model.ResetEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ResetDao extends JpaRepository<ResetEntity,Integer> {

    @Query(value = "Select * from reset_password res where res.user_id=?1",nativeQuery = true)
    public ResetEntity getOtpByUser(Integer userId);

    @Modifying
    @Transactional
    @Query(value = "Delete from reset_password res where res.user_id=?1",nativeQuery = true)
    public void removerUserOtp(Integer userId);
}
