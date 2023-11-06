package com.project.SchoolManagementSystem.address.Dao;

import com.project.SchoolManagementSystem.address.Model.AddressEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressDao extends JpaRepository<AddressEntity,Integer> {

    @Query(value = "select * from address where address.user_id=?1 and address.address_type=?2", nativeQuery = true)
    public AddressEntity getUserAddress(Integer id, String type);

    @Transactional
    @Modifying
    @Query(value = "delete from address where address.user_id=?1 and address.address_type=?2", nativeQuery = true)
    public void deleteUserAddress(Integer id, String type);
}
