package com.project.SchoolManagementSystem.resetPassword.Controller;

import com.project.SchoolManagementSystem.resetPassword.Dao.EmailRequest;
import com.project.SchoolManagementSystem.resetPassword.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/sendEmail")
public class EmailController {
    private EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/")
    public ResponseEntity<Boolean> sendMail(@RequestBody EmailRequest email)
    {
        String to = "smartboydk1016@gmail.com";
        String subject = email.getSubject();
        String body = email.getBody();
        this.emailService.sendEmail(to,subject,body);
        return ResponseEntity.ok(true);
    }
}
