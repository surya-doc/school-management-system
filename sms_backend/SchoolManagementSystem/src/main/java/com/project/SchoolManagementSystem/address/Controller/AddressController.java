package com.project.SchoolManagementSystem.address.Controller;

import com.project.SchoolManagementSystem.address.Dto.AddressDto;
import com.project.SchoolManagementSystem.address.Dto.AddressMapper;
import com.project.SchoolManagementSystem.address.Dto.UserAddressDto;
import com.project.SchoolManagementSystem.address.Service.Impl.AddressServiceImpl;
import com.project.SchoolManagementSystem.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users/address")
public class AddressController {

    private AddressServiceImpl addressService;
    private AddressMapper addressMapper;
    @Autowired
    public AddressController(AddressServiceImpl addressService, AddressMapper addressMapper)
    {
        this.addressService=addressService;
        this.addressMapper=addressMapper;
    }

    @GetMapping("/{userId}/{type}")
    public ResponseEntity<UserAddressDto> getUserAddress(@PathVariable Integer userId, @PathVariable String type) throws ResourceNotFoundException
    {
        UserAddressDto address = this.addressService.getAddressByUserId(userId,type).getBody();
        return ResponseEntity.ok(address);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<String> createUserAddress(@PathVariable Integer userId,@RequestBody AddressDto addressDto) throws ResourceNotFoundException
    {
        addressDto.setUser_id(userId);
        this.addressService.createUserAddress(userId,addressMapper.convertToEntity(addressDto));
        return ResponseEntity.ok("Created address for user with id:"+userId);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<String> updateUserAddress(@PathVariable Integer userId,@RequestBody AddressDto addressDto) throws ResourceNotFoundException
    {
        addressDto.setUser_id(userId);
        this.addressService.updateAddressOfUser(userId,addressMapper.convertToEntity(addressDto));
        return ResponseEntity.ok("Updated address for user with id:"+userId);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUserAddress(@PathVariable Integer userId) throws ResourceNotFoundException
    {
        this.addressService.deleteAddressOfUser(userId);
        return ResponseEntity.ok("Created address for user with id:"+userId);
    }
}
