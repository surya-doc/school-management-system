package com.project.SchoolManagementSystem.students.Service.Impl;

import com.cemiltokatli.passwordgenerate.Password;
import com.cemiltokatli.passwordgenerate.PasswordType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.SchoolManagementSystem.address.Dto.AddressMapper;
import com.project.SchoolManagementSystem.address.Dto.UserAddressDto;
import com.project.SchoolManagementSystem.address.Model.AddressEntity;
import com.project.SchoolManagementSystem.address.Service.Impl.AddressServiceImpl;
import com.project.SchoolManagementSystem.attendence.Service.Impl.AttendanceServiceImpl;
import com.project.SchoolManagementSystem.classes.Model.ClassEntity;
import com.project.SchoolManagementSystem.classes.Service.Impl.ClassServiceImpl;
import com.project.SchoolManagementSystem.exception.ResourceNotFoundException;
import com.project.SchoolManagementSystem.hobbies.Model.HobbyEntity;
import com.project.SchoolManagementSystem.resetPassword.Service.EmailService;
import com.project.SchoolManagementSystem.students.Dao.StudentDao;
import com.project.SchoolManagementSystem.students.Dto.*;
import com.project.SchoolManagementSystem.students.Model.StudentEntity;
import com.project.SchoolManagementSystem.students.Service.StudentService;
import com.project.SchoolManagementSystem.users.Dto.UserDto;
import com.project.SchoolManagementSystem.users.Dto.UserDtoMapper;
import com.project.SchoolManagementSystem.users.Dto.UserReqResDto;
import com.project.SchoolManagementSystem.users.Service.Impl.UserServiceImpl;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;


@Service
public class StudentServiceImpl implements StudentService {

    private StudentDao studentDao;
    private StudentMapper studentMapper;
    private UserServiceImpl userService;
    private AddressServiceImpl addressService;
    private ClassServiceImpl classService;
    private UserDtoMapper userDtoMapper;
    private AddressMapper addressMapper;
    private EmailService emailService;
    private AttendanceServiceImpl attendanceService;

    @Autowired
    public StudentServiceImpl(StudentDao studentDao, StudentMapper studentMapper, EmailService emailService, UserServiceImpl userService, AddressServiceImpl addressService, ClassServiceImpl classService, UserDtoMapper userDtoMapper, AddressMapper addressMapper, AttendanceServiceImpl attendanceService) {
        this.studentDao = studentDao;
        this.studentMapper = studentMapper;
        this.userService = userService;
        this.addressService = addressService;
        this.classService = classService;
        this.userDtoMapper = userDtoMapper;
        this.addressMapper = addressMapper;
        this.emailService = emailService;
        this.attendanceService = attendanceService;
    }

    @Override
    public ResponseEntity<List<StudentResponseDto>> getAllStudents() throws ResourceNotFoundException {
        List<UserReqResDto> allUsers = this.userService.getAllUsers().getBody();
        List<StudentResponseDto> students = allUsers.stream()
                .map(user -> {
                    try {
                        return this.studentMapper.mapToStudentResDto(user);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .filter(user -> user.getRole().equals("student")).toList();
        return ResponseEntity.ok(students);
    }

    @Override
    public ResponseEntity<List<StudentResponseDto>> getAllStudentsByPages(Integer pageNo) throws ResourceNotFoundException {
        List<UserReqResDto> allUsers = this.userService.getAllStudentsByPage(pageNo).getBody();
        List<StudentResponseDto> students = allUsers.stream()
                .map(user -> {
                    try {
                        return this.studentMapper.mapToStudentResDto(user);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .filter(user -> user.getRole().equals("student")).toList();
        return ResponseEntity.ok(students);
    }

    @Override
    public StudentDetailsDto getStudentById(Integer userId) throws ResourceNotFoundException, IOException {
        StudentEntity student = this.studentDao.findByUserId(userId);
        UserReqResDto user = this.userService.getUserById(student.getUser_id().getUser_id()).getBody();
        String[] hobbies = student.getStudent_hobbies().stream().map((e)->e.getHobbies()).toArray(String[]::new);
        UserAddressDto permanent_address = this.addressService.getAddressByUserId(userId,"permanent").getBody();
        UserAddressDto current_address = this.addressService.getAddressByUserId(userId,"current").getBody();
        StudentDetailsDto studentDetails = new StudentDetailsDto();
                studentDetails.setId(userId);
        studentDetails.setFullName(user.getFull_name());
                studentDetails.setDob(user.getDob());
                studentDetails.setEmail(user.getEmail());
                studentDetails.setPhoneNumber(user.getPh_number());
                studentDetails.setGender(user.getGender());
                studentDetails.setClassDetail(student.getStudent_class().getClass_id());
                studentDetails.setCurrent_Address(current_address);
                studentDetails.setPermanent_Address(permanent_address);
                studentDetails.setHobbies(hobbies);
                studentDetails.setProfilePic(Files.readAllBytes(new File(user.getProfile_pic()).toPath()));
                if(student.getDob_certificate() != null){
                    studentDetails.setDobCertificate(Files.readAllBytes(new File(student.getDob_certificate()).toPath()));
                }
        return studentDetails;
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public ResponseEntity<String> createStudent(MultipartFile image, String studentData) throws ResourceNotFoundException, IOException {
        String filePath = "/home/surya/Desktop/sms/images" + File.separator + image.getOriginalFilename();
        image.transferTo(new File(filePath));
        ObjectMapper objectMapper = new ObjectMapper();
        CreateStudentDataDto student = objectMapper.readValue(studentData, CreateStudentDataDto.class);
        student.setProfilePic(filePath);

        Password pass = Password.createPassword(PasswordType.ALPHANUMERIC, 8, 8);
        String password = pass.generate();

        UserDto userDto = this.userDtoMapper.mapToUserDtoFromCreateStudentDataDto(student, password);
        this.userService.createUser(userDto);

        AddressEntity currentAddress = this.addressMapper.convertToEntityFromUserAddressDto(student.getCurrent_Address(), "current", student.getId());
        this.addressService.createUserAddress(student.getId(), currentAddress);

        AddressEntity permanentAddress = this.addressMapper.convertToEntityFromUserAddressDto(student.getPermanent_Address(), "permanent", student.getId());
        this.addressService.createUserAddress(student.getId(), permanentAddress);
        StudentDto studentDto = this.studentMapper.mapToStudentDtoFromCreateStudentDataDto(student);

        StudentEntity studentEntity = this.studentMapper.convertToEntity(studentDto);
        this.studentDao.save(studentEntity);

        this.emailService.sendEmail(student.getEmail(), "Your password", password);
        return ResponseEntity.ok("Student Created");
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public ResponseEntity<String> updateStudentById(MultipartFile image, String studentData) throws ResourceNotFoundException, IOException {
        String filePath = "/home/surya/Desktop/sms/images" + File.separator + image.getOriginalFilename();
        image.transferTo(new File(filePath));
        ObjectMapper objectMapper = new ObjectMapper();
        CreateStudentDataDto student = objectMapper.readValue(studentData, CreateStudentDataDto.class);
        ClassEntity classEntity = this.classService.getClassById(student.getClassId()).getBody();
        StudentEntity student1 = this.studentDao.findByUserId(student.getId());
        student1.setStudent_class(classEntity);

        UserReqResDto userReqResDto = this.userDtoMapper.mapToUserReqResDtoFromCreateStudentDataDto(student, filePath);
        this.userService.updateUser(userReqResDto);

        AddressEntity currentAddress = this.addressMapper.convertToEntityFromUserAddressDto(student.getCurrent_Address(), "current", student.getId());
        this.addressService.updateAddressOfUser(student.getId(), currentAddress);

        AddressEntity permanentAddress = this.addressMapper.convertToEntityFromUserAddressDto(student.getPermanent_Address(), "permanent", student.getId());
        this.addressService.updateAddressOfUser(student.getId(), permanentAddress);

        this.studentDao.save(student1);
        return ResponseEntity.ok("Student updated with id:"+student.getId());
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public ResponseEntity<Object> deleteStudentById(Integer id) throws ResourceNotFoundException {
        StudentEntity student1 = this.studentDao.findByUserId(id);
        student1.getStudent_hobbies().clear();
        student1.getStudent_attendance().clear();
        this.attendanceService.deleteAttendance(student1.getStudent_id());
        this.addressService.deleteAddressOfUser(id);
        this.studentDao.delete(student1);
        return ResponseEntity.ok(Collections.singletonMap("message", "Student deleted with id:"+id));
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public ResponseEntity<Object> addHobby(Integer studentId, String hobby) throws ResourceNotFoundException {
        StudentEntity student = this.studentDao.findByUserId(studentId);
        if(student == null){
            throw new ResourceNotFoundException("Student does not exist with this id " + studentId);
        }

        HobbyEntity hobbyEntity = new HobbyEntity();
        hobbyEntity.setHobbies(hobby);
        student.getStudent_hobbies().add(hobbyEntity);
        this.studentDao.save(student);
        return ResponseEntity.ok(Collections.singletonMap("message", "Hobby Added"));
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public ResponseEntity<Object> addDobCertificate(Integer studentId, MultipartFile dobCertificate) throws ResourceNotFoundException, IOException {
        StudentEntity student = this.studentDao.findByUserId(studentId);
        if(student == null){
            throw new ResourceNotFoundException("Student does not exist with this id " + studentId);
        }
        String filePath = "/home/surya/Desktop/sms/images" + File.separator + dobCertificate.getOriginalFilename();
        dobCertificate.transferTo(new File(filePath));
        student.setDob_certificate(filePath);
        this.studentDao.save(student);
        return ResponseEntity.ok(Collections.singletonMap("message", "DOB certificate Added"));
    }

    @Override
    public ResponseEntity<List<StudentResponseDto>> getStudentsByClassId(Integer classId, Integer pageNo) throws ResourceNotFoundException {
        List<StudentEntity> allUsers = this.studentDao.findByClassId(classId, 10, 10*pageNo);
        List<StudentResponseDto> students = allUsers.stream()
                .map(user -> {
                    try {
                        return this.studentMapper.mapToStudentResDto(Objects.requireNonNull(this.userService.getUserById(user.getUser_id().getUser_id()).getBody()));
                    } catch (IOException | ResourceNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();
        return ResponseEntity.ok(students);
    }

    @Override
    public ResponseEntity<List<StudentResponseDto>> getStudentsByAttendance(Integer classId) throws ResourceNotFoundException {
        List<Integer> allUsers = this.studentDao.studentByAttendance(classId);
        List<StudentEntity> studentEntitiesList= new ArrayList<>();
        for(Integer id:allUsers){
            StudentEntity student = this.studentDao.findById(id).orElseThrow();
            studentEntitiesList.add(student);
        }
        List<StudentResponseDto> students = studentEntitiesList.stream()
                .map(user -> {
                    try {
                        return this.studentMapper.mapToStudentResDto(Objects.requireNonNull(this.userService.getUserById(user.getUser_id().getUser_id()).getBody()));
                    } catch (IOException | ResourceNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();
        return ResponseEntity.ok(students);
    }

    @Override
    public ResponseEntity<Integer> getStudentDetailByUserId(Integer userId) throws ResourceNotFoundException {
        StudentEntity student = this.studentDao.findByUserId(userId);
        return ResponseEntity.ok(student.getStudent_id());
    }
}
