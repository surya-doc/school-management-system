package com.project.SchoolManagementSystem.address.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserAddressDto {
    private String street_address;
    private String city;
    private String state;
    private Integer postal_code;
    private String country;
}
