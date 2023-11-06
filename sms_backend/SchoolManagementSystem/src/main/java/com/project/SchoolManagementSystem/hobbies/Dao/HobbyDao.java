package com.project.SchoolManagementSystem.hobbies.Dao;

import com.project.SchoolManagementSystem.hobbies.Model.HobbyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HobbyDao extends JpaRepository<HobbyEntity,Integer> {
}
