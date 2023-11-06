package com.project.SchoolManagementSystem.address.Dto;

import com.project.SchoolManagementSystem.address.Model.AddressEntity;
import com.project.SchoolManagementSystem.exception.ResourceNotFoundException;
import com.project.SchoolManagementSystem.users.Model.UserEntity;
import com.project.SchoolManagementSystem.users.Service.Impl.UserServiceImpl;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

    private UserServiceImpl userService;
    public AddressMapper(UserServiceImpl userService)
    {
        this.userService=userService;
    }
    public UserAddressDto convertToDto(AddressEntity addressEntity)
    {
        UserAddressDto userAddressDto = new UserAddressDto();

                userAddressDto.setStreet_address(addressEntity.getStreet_address());
                userAddressDto.setCity(addressEntity.getCity());
                userAddressDto.setState(addressEntity.getState());
                userAddressDto.setPostal_code(addressEntity.getPostal_code());
                userAddressDto.setCountry(addressEntity.getCountry());
                return userAddressDto;
    }

    public AddressEntity convertToEntity(AddressDto addressDto) throws ResourceNotFoundException
    {
        UserEntity user = this.userService.getUserDetailsById(addressDto.getUser_id()).getBody();

        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setAddress_id(addressDto.getAddress_id());
        addressEntity.setStreet_address(addressDto.getStreet_address());
        addressEntity.setCity(addressDto.getCity());
        addressEntity.setState(addressDto.getState());
        addressEntity.setPostal_code(addressDto.getPostal_code());
        addressEntity.setCountry(addressDto.getCountry());
        addressEntity.setType(addressDto.getType());
        addressEntity.setUser_id(user);
        return addressEntity;
    }

    public AddressEntity convertToEntityFromUserAddressDto(UserAddressDto userAddressDto, String type, Integer userId) throws ResourceNotFoundException {
        UserEntity user = this.userService.getUserDetailsById(userId).getBody();
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setUser_id(null);
        addressEntity.setStreet_address(userAddressDto.getStreet_address());
        addressEntity.setCity(userAddressDto.getCity());
        addressEntity.setState(userAddressDto.getState());
        addressEntity.setPostal_code(userAddressDto.getPostal_code());
        addressEntity.setCountry(userAddressDto.getCountry());
        addressEntity.setType(type);
        addressEntity.setUser_id(user);
        return addressEntity;
    }

}
