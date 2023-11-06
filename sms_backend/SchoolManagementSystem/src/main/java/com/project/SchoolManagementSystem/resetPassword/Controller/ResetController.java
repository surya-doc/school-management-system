package com.project.SchoolManagementSystem.resetPassword.Controller;

import com.project.SchoolManagementSystem.exception.ResourceNotFoundException;
import com.project.SchoolManagementSystem.resetPassword.Service.ResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/auth")
public class ResetController {
    private ResetService resetService;

    @Autowired
    public ResetController(ResetService resetService) {
        this.resetService = resetService;
    }

    @PostMapping("/validate-email")
    public ResponseEntity<Integer> emailValidation(@RequestParam String email) throws UsernameNotFoundException {
        String username = email.replaceAll("^\"|\"$", "");
        System.out.println(username);
        return this.resetService.validateUser(username);
    }

    @PostMapping("/generate-otp")
    public void generateOtp(@RequestParam Integer userId) throws ResourceNotFoundException {
        System.out.println(userId);
        this.resetService.generateOtp(userId);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Boolean> resetPassword(@RequestParam Integer userId, @RequestParam String newPassword, @RequestParam String otp) throws ResourceNotFoundException {
        Boolean isValid = this.resetService.validateOtp(userId,otp).getBody();
        if(isValid)
        {
            return this.resetService.changeUserPassword(userId, newPassword);
        }
        return ResponseEntity.badRequest().body(false);
    }
}
