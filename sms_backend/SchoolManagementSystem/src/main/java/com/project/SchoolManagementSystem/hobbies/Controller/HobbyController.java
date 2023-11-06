package com.project.SchoolManagementSystem.hobbies.Controller;

import com.project.SchoolManagementSystem.exception.ResourceNotFoundException;
import com.project.SchoolManagementSystem.hobbies.Model.HobbyEntity;
import com.project.SchoolManagementSystem.hobbies.Service.Impl.HobbyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/hobbies")
public class HobbyController {
    private HobbyServiceImpl hobbyService;

    @Autowired
    public HobbyController(HobbyServiceImpl hobbyService) {
        this.hobbyService = hobbyService;
    }

    @GetMapping("/{userId}")
    public List<HobbyEntity> getUserHobbies(@PathVariable Integer userId) throws ResourceNotFoundException
    {
        List<HobbyEntity> hobbies = this.hobbyService.getUserHobbies(userId).getBody();
        return hobbies;
    }

    @PostMapping("/{userId}")
    public void addUserHobby(@PathVariable Integer userId,@RequestBody HobbyEntity hobby) throws ResourceNotFoundException, IOException {
        String msg = this.hobbyService.createUserHobbies(userId,hobby).getBody();
    }

    @DeleteMapping("/{userId}")
    public void deleteUserHobby(@PathVariable Integer userId,@RequestBody HobbyEntity hobby) throws ResourceNotFoundException {
        String msg = this.hobbyService.deleteUserHobbies(userId,hobby).getBody();
    }
}
