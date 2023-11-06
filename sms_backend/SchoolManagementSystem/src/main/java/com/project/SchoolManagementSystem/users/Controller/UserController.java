package com.project.SchoolManagementSystem.users.Controller;

import com.project.SchoolManagementSystem.exception.ResourceNotFoundException;
import com.project.SchoolManagementSystem.users.Dto.UserDto;
import com.project.SchoolManagementSystem.users.Dto.UserLoginDto;
import com.project.SchoolManagementSystem.users.Dto.UserReqResDto;
import com.project.SchoolManagementSystem.users.Service.Impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class UserController {
    private UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserReqResDto> getUserDetailsById(@PathVariable Integer id) throws ResourceNotFoundException {
        return this.userService.getUserById(id);
    }

    @GetMapping("/fetch-user/{email}")
    public UserLoginDto getUserByEmail(@PathVariable String email) throws UsernameNotFoundException {

        return this.userService.getUserDetailsByEmail(email).getBody();
    }

    @PostMapping("/user")
    public ResponseEntity<String> createUser(@RequestBody UserDto userDto) throws ResourceNotFoundException {
        return this.userService.createUser(userDto);
    }

    @PutMapping("/user")
    public ResponseEntity<String> updateUser(@RequestBody UserReqResDto userReqResDto) throws ResourceNotFoundException {
        return this.userService.updateUser(userReqResDto);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer id) throws ResourceNotFoundException {
        return this.userService.deleteUser(id);
    }

    @PutMapping("/user/updatepassword/{userId}")
    public ResponseEntity<Object> updatePassword(
            @PathVariable Integer userId,
            @RequestParam("passwordUpdateData") String updatePasswordRequestDto
    ) throws ResourceNotFoundException, IOException {
        return this.userService.updatePassword(userId, updatePasswordRequestDto);
    }

    @PutMapping("/user/image/{userId}")
    public ResponseEntity<Object> updateProfilePic(
            @PathVariable Integer userId,
            @RequestParam(value = "profileimage",required = false) MultipartFile image) throws IOException, ResourceNotFoundException {
        return this.userService.updateUserProfilePic(image, userId);
    }
}
