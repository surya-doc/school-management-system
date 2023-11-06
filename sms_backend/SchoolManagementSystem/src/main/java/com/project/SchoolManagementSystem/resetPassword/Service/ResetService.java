package com.project.SchoolManagementSystem.resetPassword.Service;

import com.project.SchoolManagementSystem.exception.ResourceNotFoundException;
import com.project.SchoolManagementSystem.resetPassword.Dao.ResetDao;
import com.project.SchoolManagementSystem.resetPassword.Model.ResetEntity;
import com.project.SchoolManagementSystem.users.Dto.UserDtoMapper;
import com.project.SchoolManagementSystem.users.Dto.UserLoginDto;
import com.project.SchoolManagementSystem.users.Model.UserEntity;
import com.project.SchoolManagementSystem.users.Service.Impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class ResetService {

    private ResetDao resetDao;
    private UserServiceImpl userService;

    private EmailService emailService;
    private UserDtoMapper userDtoMapper;
    private PasswordEncoder passwordEncoder;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @Autowired
    public ResetService(ResetDao resetDao,UserServiceImpl userService,UserDtoMapper userDtoMapper, EmailService emailService,PasswordEncoder passwordEncoder) {
        this.resetDao = resetDao;
        this.userService = userService;
        this.userDtoMapper = userDtoMapper;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<String> generateOtp(Integer userId) throws ResourceNotFoundException {
        this.resetDao.removerUserOtp(userId);
        int otpLength = 6;
        Random random = new Random();
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < otpLength; i++) {
            otp.append(random.nextInt(10));
        }
        ResetEntity resetData = new ResetEntity();
        resetData.setUser_id(userId);
        resetData.setOtp(otp.toString());
        this.resetDao.save(resetData);
        UserEntity user = this.userService.getUserDetailsById(userId).getBody();
        String to = "smartboydk1016@gmail.com";
        String subject = "One Time Validation for password reset on Argus School";
        String body = "One Time Validation for password reset on Argus School, Kindly reset your password in 10 mins OTP:-"+otp.toString();
        emailService.sendEmail(to,subject,body);
        scheduleOtpExpiration(userId);
        return ResponseEntity.ok("Otp Generated and sent to user mail suceefully");
    }

    public ResponseEntity<Boolean> validateOtp(Integer userId,String otp)
    {
        ResetEntity resetEntity = this.resetDao.getOtpByUser(userId);
        System.out.println(Objects.equals(resetEntity.getOtp(), otp));
        if(Objects.equals(resetEntity.getOtp(), otp))
        {
            return ResponseEntity.ok(Objects.equals(resetEntity.getOtp(), otp));
        }
        return ResponseEntity.badRequest().body(false);
    }

    public ResponseEntity<Integer> validateUser(String email) throws UsernameNotFoundException
    {
        UserLoginDto user = this.userService.getUserDetailsByEmail(email).getBody();
        System.out.println(user);
        System.out.println(user);
        return ResponseEntity.ok(user.getId());
    }

    private void scheduleOtpExpiration(Integer userId) {
        int expirationMinutes = 10;

        scheduler.schedule(() -> {
            this.resetDao.removerUserOtp(userId);
            System.out.println("OTP for " + userId + " expired.");
        }, expirationMinutes, TimeUnit.MINUTES);
    }

    public ResponseEntity<Boolean> changeUserPassword(Integer userId, String newPassword) throws ResourceNotFoundException {
        UserEntity user = this.userService.getUserDetailsById(userId).getBody();
        user.setPassword(this.passwordEncoder.encode(newPassword));
        this.userService.updateUserDetails(user);
        return ResponseEntity.ok(true);
    }
}
