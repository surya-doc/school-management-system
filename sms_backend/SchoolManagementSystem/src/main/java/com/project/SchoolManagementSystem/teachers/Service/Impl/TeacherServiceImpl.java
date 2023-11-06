package com.project.SchoolManagementSystem.teachers.Service.Impl;

import com.cemiltokatli.passwordgenerate.Password;
import com.cemiltokatli.passwordgenerate.PasswordType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.SchoolManagementSystem.address.Dto.AddressMapper;
import com.project.SchoolManagementSystem.address.Dto.UserAddressDto;
import com.project.SchoolManagementSystem.address.Model.AddressEntity;
import com.project.SchoolManagementSystem.address.Service.Impl.AddressServiceImpl;
import com.project.SchoolManagementSystem.classes.Dao.ClassDao;
import com.project.SchoolManagementSystem.classes.Model.ClassEntity;
import com.project.SchoolManagementSystem.exception.ResourceNotFoundException;
import com.project.SchoolManagementSystem.resetPassword.Service.EmailService;
import com.project.SchoolManagementSystem.subjects.Dao.SubjectDao;
import com.project.SchoolManagementSystem.subjects.Model.SubjectEntity;
import com.project.SchoolManagementSystem.teachers.Dao.TeacherDao;
import com.project.SchoolManagementSystem.teachers.Dto.*;
import com.project.SchoolManagementSystem.teachers.Model.TeacherEntity;
import com.project.SchoolManagementSystem.teachers.Service.TeacherService;
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
import java.util.Collections;
import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {

    private TeacherDao teacherDao;
    private TeacherMapper teacherMapper;
    private UserServiceImpl userService;
    private SubjectDao subjectDao;
    private ClassDao classDao;
    private AddressServiceImpl addressService;
    private UserDtoMapper userDtoMapper;
    private AddressMapper addressMapper;
    private EmailService emailService;

    @Autowired
    public TeacherServiceImpl(TeacherDao teacherDao, TeacherMapper teacherMapper, UserServiceImpl userService, SubjectDao subjectDao, ClassDao classDao, AddressServiceImpl addressService, UserDtoMapper userDtoMapper, EmailService emailService, AddressMapper addressMapper) {
        this.teacherDao = teacherDao;
        this.teacherMapper = teacherMapper;
        this.userService = userService;
        this.subjectDao = subjectDao;
        this.classDao = classDao;
        this.addressService = addressService;
        this.userDtoMapper = userDtoMapper;
        this.addressMapper = addressMapper;
        this.emailService = emailService;
    }

    @Override
    public ResponseEntity<List<TeacherResponseDto>> getAllTeachers() throws ResourceNotFoundException {
        List<UserReqResDto> allUsers = this.userService.getAllUsers().getBody();
        assert allUsers != null;
        List<TeacherResponseDto> teachers = allUsers.stream()
                .map(user -> {
                    try {
                        return this.teacherMapper.mapToTeacherResDto(user);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (ResourceNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                })
                .filter(user -> user.getRole().equals("teacher")).toList();
        return ResponseEntity.ok(teachers);
    }

    @Override
    public ResponseEntity<List<TeacherResponseDto>> getTeachersByPage(Integer pageNo) throws ResourceNotFoundException {
        List<UserReqResDto> allUsers = this.userService.getAllTeachersByPage(pageNo).getBody();
        assert allUsers != null;
        List<TeacherResponseDto> teachers = allUsers.stream()
                .map(user -> {
                    try {
                        return this.teacherMapper.mapToTeacherResDto(user);
                    } catch (IOException | ResourceNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                })
                .filter(user -> user.getRole().equals("teacher")).toList();
        return ResponseEntity.ok(teachers);
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public ResponseEntity<String> createTeacher(MultipartFile image, String teacherData) throws ResourceNotFoundException, IOException {
        String filePath = "/home/surya/Desktop/sms/images" + File.separator + image.getOriginalFilename();
        image.transferTo(new File(filePath));
        ObjectMapper objectMapper = new ObjectMapper();
        CreateTeacherDataDto teacher = objectMapper.readValue(teacherData, CreateTeacherDataDto.class);
        Password pass = Password.createPassword(PasswordType.ALPHANUMERIC, 8, 8);
        String password = pass.generate();
        UserDto userDto = this.userDtoMapper.mapToUserDtoFromCreateTeacherDataDto(teacher, password, filePath);
        this.userService.createUser(userDto);
        AddressEntity currentAddress = this.addressMapper.convertToEntityFromUserAddressDto(teacher.getCurrent_Address(), "current", teacher.getId());
        this.addressService.createUserAddress(teacher.getId(), currentAddress);
        AddressEntity permanentAddress = this.addressMapper.convertToEntityFromUserAddressDto(teacher.getPermanent_Address(), "permanent", teacher.getId());
        this.addressService.createUserAddress(teacher.getId(), permanentAddress);
        TeacherDto teacherDto = this.teacherMapper.mapToTeacherDtoFromCreateTeacherDataDto(teacher);
        TeacherEntity teacherEntity = this.teacherMapper.mapToTeacherEntity(teacherDto);
        this.teacherDao.save(teacherEntity);
        String[] classes = teacher.getClasses();
        for (String aClass : classes) {
            Integer classId = Integer.parseInt(aClass);
            this.assignClassToTeacher(teacher.getId(), classId);
        }
        String[] subjects = teacher.getSubjects();
        for (String subject : subjects) {
            Integer subjectId = Integer.parseInt(subject);
            this.assignSubjectToTeacher(teacher.getId(), subjectId);
        }

        this.emailService.sendEmail(teacher.getEmail(), "Your password", password);
        return ResponseEntity.ok("Teacher created");
    }

    @Override
    public ResponseEntity<TeacherDetailsDto> getTeacherById(Integer userId) throws ResourceNotFoundException, IOException {
        TeacherEntity teacher = this.teacherDao.findByUserId(userId);
        UserReqResDto user = this.userService.getUserById(teacher.getUser_id().getUser_id()).getBody();
        ClassEntity[] classes = teacher.getClassEntityList().toArray(ClassEntity[]::new);
        SubjectEntity[] subjects = teacher.getSubjectEntityList().toArray(SubjectEntity[]::new);
        UserAddressDto permanent_address = this.addressService.getAddressByUserId(userId,"permanent").getBody();
        UserAddressDto current_address = this.addressService.getAddressByUserId(userId,"current").getBody();

        TeacherDetailsDto teacherDetails = new TeacherDetailsDto(userId, user.getFull_name(), user.getDob(),
                user.getEmail(), user.getPh_number(), teacher.getQualification(), subjects, classes, user.getGender(), current_address, permanent_address,
                Files.readAllBytes(new File(user.getProfile_pic()).toPath()));
        return ResponseEntity.ok(teacherDetails);
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public ResponseEntity<String> updateTeacher(MultipartFile image, String teacherData) throws ResourceNotFoundException, IOException {

        String filePath = "/home/surya/Desktop/sms/images" + File.separator + image.getOriginalFilename();
        image.transferTo(new File(filePath));

        ObjectMapper objectMapper = new ObjectMapper();
        CreateTeacherDataDto teacher = objectMapper.readValue(teacherData, CreateTeacherDataDto.class);

        UserReqResDto userReqResDto = this.userDtoMapper.mapToUserReqResDtoFromCreateTeacherDataDto(teacher, filePath);
        this.userService.updateUser(userReqResDto);
        AddressEntity currentAddress = this.addressMapper.convertToEntityFromUserAddressDto(teacher.getCurrent_Address(), "current", teacher.getId());
        this.addressService.updateAddressOfUser(teacher.getId(), currentAddress);
        AddressEntity permanentAddress = this.addressMapper.convertToEntityFromUserAddressDto(teacher.getPermanent_Address(), "permanent", teacher.getId());
        this.addressService.updateAddressOfUser(teacher.getId(), permanentAddress);


        TeacherEntity teacherEntity = this.teacherDao.findByUserId(teacher.getId());
        if(teacherEntity == null){
            throw new  ResourceNotFoundException("Teacher does not exist with this id"+teacher.getId());
        }
        teacherEntity.setQualification(teacher.getQualification());
        this.teacherDao.save(teacherEntity);
        String[] classes = teacher.getClasses();
        if(classes != null){
            for (String aClass : classes) {
                Integer classId = Integer.parseInt(aClass);
                this.assignClassToTeacher(teacher.getId(), classId);
            }
        }
        String[] subjects = teacher.getSubjects();
        if(subjects != null){
            for (String subject : subjects) {
                Integer subjectId = Integer.parseInt(subject);
                this.assignSubjectToTeacher(teacher.getId(), subjectId);
            }
        }

        return ResponseEntity.ok("Teacher updated");
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public ResponseEntity<Object> deleteTeacher(Integer userId) throws ResourceNotFoundException {
        TeacherEntity teacherEntity = this.teacherDao.findByUserId(userId);
        ClassEntity classEntity = this.classDao.getClassByTeacherId(userId);
        if(classEntity != null){
            classEntity.setTeacher_id(null);
            this.classDao.save(classEntity);
        }
        if (teacherEntity == null) {
            throw new ResourceNotFoundException("Teacher does not exist with this id " + userId);
        }

        teacherEntity.getSubjectEntityList().clear();
        teacherEntity.getClassEntityList().clear();

        this.teacherDao.delete(teacherEntity);

        return ResponseEntity.ok(Collections.singletonMap("message", "Teacher deleted"));
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public ResponseEntity<String> assignSubjectToTeacher(Integer userId, Integer subjectId) throws ResourceNotFoundException {
        TeacherEntity teacher = this.teacherDao.findByUserId(userId);
        if(teacher == null){
            throw new ResourceNotFoundException("Teacher does not exist with this id " + userId);
        }

        SubjectEntity subject = this.subjectDao.findById(subjectId)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found with ID: " + subjectId));

        teacher.getSubjectEntityList().add(subject);
        this.teacherDao.save(teacher);

        return ResponseEntity.ok("Teacher associated with the subject.");
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public ResponseEntity<String> assignClassToTeacher(Integer userId, Integer classId) throws ResourceNotFoundException {
        TeacherEntity teacher = this.teacherDao.findByUserId(userId);
        if(teacher == null){
            throw new ResourceNotFoundException("Teacher does not exist with this id " + userId);
        }

        ClassEntity classEntity = this.classDao.findById(classId)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found with ID: " + classId));

        teacher.getClassEntityList().add(classEntity);
        this.teacherDao.save(teacher);

        return ResponseEntity.ok("Teacher associated with the class.");
    }

    @Override
    public ResponseEntity<List<TeacherResponseDto>> getTeachersByQualification(String qualification){
        List<TeacherEntity> classes = this.getTeachersNotInClass();
        Integer classMinQualification = getQualificationIndex(qualification);
        List<TeacherResponseDto> updatedTeachers = classes.stream()
                .filter((e) -> {
                    System.out.println(e.getQualification());
                    return getQualificationIndex(e.getQualification()) >= classMinQualification;

                })
                .map((e) -> {
                    try {
                        return this.teacherMapper.maptoTeacherResDtoFromTeacherEntity(e);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                })
                .toList();
        System.out.println(updatedTeachers);
        return ResponseEntity.ok(updatedTeachers);
    }

    public Integer getQualificationIndex(String qualification){
        Integer index = switch (qualification) {
            case "B.Sc" -> 1;
            case "M.Sc" -> 2;
            case "PhD" -> 3;
            default -> 1;
        };
        return index;
    }

    @Override
    public ResponseEntity<Integer> getTeacherByUserId(Integer userId) throws ResourceNotFoundException {
        TeacherEntity teacher = this.teacherDao.findByUserId(userId);
        return ResponseEntity.ok(teacher.getTeacher_id());
    }
    public List<TeacherEntity> getTeachersNotInClass() {
        return this.teacherDao.findTeachersNotInClass();
    }
}
