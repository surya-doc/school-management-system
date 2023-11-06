package com.project.SchoolManagementSystem.address.Service;

import com.project.SchoolManagementSystem.address.Dto.UserAddressDto;
import com.project.SchoolManagementSystem.address.Model.AddressEntity;
import com.project.SchoolManagementSystem.exception.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;

public interface AddressService {
    public ResponseEntity<UserAddressDto> getAddressByUserId(Integer userId, String type) throws ResourceNotFoundException;
    public ResponseEntity<String> createUserAddress(Integer userId, AddressEntity address) throws ResourceNotFoundException;
    public ResponseEntity<String> updateAddressOfUser(Integer userId, AddressEntity address) throws ResourceNotFoundException;
    public ResponseEntity<String> deleteAddressOfUser(Integer userId) throws ResourceNotFoundException;
}
