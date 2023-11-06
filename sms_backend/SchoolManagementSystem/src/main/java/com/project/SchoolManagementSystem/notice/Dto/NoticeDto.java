package com.project.SchoolManagementSystem.notice.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NoticeDto {
    private String heading;
    private String description;
    private Date startDate;
    private Date expiryDate;
    private String image;
}
