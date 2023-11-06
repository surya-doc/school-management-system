package com.project.SchoolManagementSystem.users.Service.Impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.SchoolManagementSystem.classes.Dto.ClassResDto;
import com.project.SchoolManagementSystem.classes.Model.ClassEntity;
import com.project.SchoolManagementSystem.exception.ResourceNotFoundException;
import com.project.SchoolManagementSystem.users.Dao.UserDao;
import com.project.SchoolManagementSystem.users.Dto.*;
import com.project.SchoolManagementSystem.users.Model.UserEntity;
import com.project.SchoolManagementSystem.users.Service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserDao userDao;
    private UserDtoMapper userDtoMapper;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao, UserDtoMapper userDtoMapper, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.userDtoMapper = userDtoMapper;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public ResponseEntity<List<UserReqResDto>> getAllUsers() throws ResourceNotFoundException {
        List<UserEntity> users = this.userDao.findAll();
        System.out.println("user"+users);
        List<UserReqResDto> allUserDtoLists = users.stream()
                .map(user -> this.userDtoMapper.mapToUserResponseDto(user))
                .toList();
        return ResponseEntity.ok(allUserDtoLists);
    }

    @Override
    public ResponseEntity<List<UserReqResDto>> getAllStudentsByPage(Integer pageNo) throws ResourceNotFoundException {
        Pageable pageRequest = PageRequest.of(pageNo, 10);
        List<UserEntity> users = this.userDao.findByRole("student", pageRequest).getContent();
        System.out.println("user"+users);
        List<UserReqResDto> allUserDtoLists = users.stream()
                .map(user -> this.userDtoMapper.mapToUserResponseDto(user))
                .toList();
        return ResponseEntity.ok(allUserDtoLists);
    }

    @Override
    public ResponseEntity<List<UserReqResDto>> getAllTeachersByPage(Integer pageNo) throws ResourceNotFoundException {
        Pageable pageRequest = PageRequest.of(pageNo, 10);
        List<UserEntity> users = this.userDao.findByRole("teacher", pageRequest).getContent();
        System.out.println("user"+users);
        List<UserReqResDto> allUserDtoLists = users.stream()
                .map(user -> this.userDtoMapper.mapToUserResponseDto(user))
                .toList();
        return ResponseEntity.ok(allUserDtoLists);
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public ResponseEntity<String> createUser(UserDto user) throws ResourceNotFoundException {
        Integer userId = user.getUser_id();
        Optional<UserEntity> userDetails = userDao.findById(userId);
        if(userDetails.isPresent()){
            throw new ResourceNotFoundException("User already exists with id" + userId);
        }
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        UserEntity userEntity = this.userDtoMapper.mapToUserEntity(user);
        this.userDao.save(userEntity);
        return ResponseEntity.ok("Created User");
    }

    /**
     * Retrieve user details by the specified user ID for frontend display.
     * The response does not include the user's password.
     *
     * @param userId The unique identifier for the user.
     * @return A ResponseEntity containing the user details (excluding password) if found.
     * @throws ResourceNotFoundException If no user exists with the given user ID.
     */
    @Override
    public ResponseEntity<UserReqResDto> getUserById(Integer userId) throws ResourceNotFoundException {
        UserEntity userDetail= this.userDao.findById(userId).orElseThrow(()->new ResourceNotFoundException("User doesnt exist with id :" +userId));
        UserReqResDto userReqResDto = this.userDtoMapper.mapToUserResponseDto(userDetail);
        return ResponseEntity.ok(userReqResDto);
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public ResponseEntity<String> updateUser(UserReqResDto user) throws ResourceNotFoundException {
        UserEntity existingUserEntity = this.userDao.findById(user.getUser_id())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + user.getUser_id()));
        getUpdatedUserEntity(existingUserEntity, user);
        this.userDao.save(existingUserEntity);
        return ResponseEntity.ok("User updated.");
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public ResponseEntity<String> deleteUser(Integer userId) throws ResourceNotFoundException {
        Optional<UserEntity> userDetails = this.userDao.findById(userId);
        if(userDetails.isEmpty()){
            throw new ResourceNotFoundException("User does not exists with this id " + userId);
        }
        this.userDao.deleteByUserId(userId);
        return ResponseEntity.ok("User deleted");
    }

    /**
     * Retrieve user details with password by the specified user ID.
     *
     * @param userId The unique identifier for the user.
     * @return A ResponseEntity containing the user details if found.
     * @throws ResourceNotFoundException If no user exists with the given user ID.
     */
    @Override
    public ResponseEntity<UserEntity> getUserDetailsById(Integer userId) throws ResourceNotFoundException {
        UserEntity userDetail= this.userDao.findById(userId).orElseThrow(()->new ResourceNotFoundException("User doesnt exist with id :" +userId));
        return ResponseEntity.ok(userDetail);
    }

    public UserEntity getUpdatedUserEntity(UserEntity existingUserEntity, UserReqResDto userReqResDto){
        Field[] fields = UserReqResDto.class.getDeclaredFields();
        for(Field field : fields){
            try {
                field.setAccessible(true);
                Object value = field.get(userReqResDto);

                if (value != null && !field.getName().equals("user_id")){
                    Field targetField = UserEntity.class.getDeclaredField(field.getName());
                    targetField.setAccessible(true);
                    targetField.set(existingUserEntity, value);
                }
            } catch (Exception exception){
                System.out.println(exception);
            }
        }
        return existingUserEntity;
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public ResponseEntity<String> updateUserDetails(UserEntity user) throws ResourceNotFoundException {
        UserEntity existingUserEntity = this.userDao.findById(user.getUser_id())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + user.getUser_id()));
        this.userDao.save(user);
        return ResponseEntity.ok("User updated.");
    }

    @Override
    public ResponseEntity<UserLoginDto> getUserDetailsByEmail(String email) throws UsernameNotFoundException {
        UserEntity user = this.userDao.findByEmail(email);

        UserLoginDto user1 = new UserLoginDto(user.getUser_id(), user.getEmail(), user.getPassword(), user.getRole());
        return new ResponseEntity<>(user1, HttpStatus.OK);
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public ResponseEntity<Object> updateUserProfilePic(MultipartFile profilePic, Integer userId) throws UsernameNotFoundException, IOException, ResourceNotFoundException {
        String filePath = "/home/surya/Desktop/sms/images" + File.separator + profilePic.getOriginalFilename();
        profilePic.transferTo(new File(filePath));
        UserEntity user = this.userDao.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User does not exists with this id."));
        user.setProfile_pic(filePath);
        this.userDao.save(user);
        return ResponseEntity.ok(Collections.singletonMap("message", "User profile picture updated successfully."));
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseEntity<Object> updatePassword(Integer userId, String passwordDetails) throws ResourceNotFoundException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        UpdatePasswordRequestDto details = objectMapper.readValue(passwordDetails, UpdatePasswordRequestDto.class);
        Optional<UserEntity> userOptional = this.userDao.findById(userId);
        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();

            String password = passwordEncoder.encode(details.getNewPassword());

            user.setPassword(password);

            this.userDao.save(user);
        }
        return ResponseEntity.ok(Collections.singletonMap("message", "Password updated successfully."));
    }
}
