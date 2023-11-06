package com.project.SchoolManagementSystem.address.Service.Impl;

import com.project.SchoolManagementSystem.address.Dao.AddressDao;
import com.project.SchoolManagementSystem.address.Dto.AddressMapper;
import com.project.SchoolManagementSystem.address.Dto.UserAddressDto;
import com.project.SchoolManagementSystem.address.Model.AddressEntity;
import com.project.SchoolManagementSystem.address.Service.AddressService;
import com.project.SchoolManagementSystem.exception.ResourceNotFoundException;
import com.project.SchoolManagementSystem.users.Model.UserEntity;
import com.project.SchoolManagementSystem.users.Service.Impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {

    private AddressDao addressDao;
    private UserServiceImpl userService;

    private AddressMapper addressMapper;

    @Autowired
    public AddressServiceImpl(AddressDao addressDao, UserServiceImpl userService, AddressMapper addressMapper){
        this.addressDao = addressDao;
        this.userService = userService;
        this.addressMapper = addressMapper;
    }

    @Override
    public ResponseEntity<UserAddressDto> getAddressByUserId(Integer userId, String type) throws ResourceNotFoundException{
        AddressEntity address = this.addressDao.getUserAddress(userId, type);
        return ResponseEntity.ok(this.addressMapper.convertToDto(address));
    }

    @Override
    public ResponseEntity<String> createUserAddress(Integer userId, AddressEntity address) throws ResourceNotFoundException{
        UserEntity user = this.userService.getUserDetailsById(userId).getBody();
        address.setUser_id(user);
        this.addressDao.save(address);
        return ResponseEntity.ok("Created Address for user with id:"+userId);
    }

    @Override
    public ResponseEntity<String> updateAddressOfUser(Integer userId, AddressEntity address)throws ResourceNotFoundException {
        UserEntity user = this.userService.getUserDetailsById(userId).getBody();
        address.setUser_id(user);
        this.addressDao.deleteUserAddress(userId, address.getType());
        this.addressDao.save(address);
        return ResponseEntity.ok("Created Address for user with id:"+userId);
    }

    @Override
    public ResponseEntity<String> deleteAddressOfUser(Integer userId) throws ResourceNotFoundException{
        this.addressDao.deleteUserAddress(userId, "current");
        this.addressDao.deleteUserAddress(userId, "permanent");
        return ResponseEntity.ok("Deleted User Address");
    }
}
