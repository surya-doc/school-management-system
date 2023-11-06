package com.project.SchoolManagementSystem.notice.Dto;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class NoticeResDto {
    private Integer notice_id;
    private String heading;
    private String description;
    private Date startDate;
    private Date expiryDate;
    private byte[] image;
    private boolean status;
}