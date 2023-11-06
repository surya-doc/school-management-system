package com.project.SchoolManagementSystem.notice.Dto;

import com.project.SchoolManagementSystem.notice.Model.NoticeEntity;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;

@Component
public class NoticeMapper {
    public NoticeDto convertToDto(NoticeEntity noticeEntity)
    {
        NoticeDto noticeDto = new NoticeDto();
        noticeDto.setHeading(noticeEntity.getHeading());
        noticeDto.setDescription(noticeEntity.getDescription());
        noticeDto.setStartDate(noticeEntity.getStart_date());
        noticeDto.setExpiryDate(noticeEntity.getExpiry_date());
        noticeDto.setImage(noticeEntity.getNotice_image());
        return noticeDto;
    }

    public NoticeEntity convertToEntity(NoticeDto noticeDto)
    {
        NoticeEntity noticeEntity = new NoticeEntity();
        noticeEntity.setId(null);
        noticeEntity.setHeading(noticeDto.getHeading());
        noticeEntity.setDescription(noticeDto.getDescription());
        noticeEntity.setStart_date(noticeDto.getStartDate());
        noticeEntity.setExpiry_date(noticeDto.getExpiryDate());
        noticeEntity.setNotice_image(noticeDto.getImage());
        noticeEntity.setStatus(noticeDto.getExpiryDate().getTime() >= new Date().getTime());
        return noticeEntity;
    }

    public NoticeResDto convertToNoticeResDto(NoticeEntity noticeEntity) throws IOException {
        Integer id = noticeEntity.getId();
        String heading = noticeEntity.getHeading();
        String body = noticeEntity.getDescription();
        Date startDate = noticeEntity.getStart_date();
        Date expiryDate = noticeEntity.getExpiry_date();
        byte[] image;


        // Create the DTO and set the values
        NoticeResDto noticeWithImageDTO = new NoticeResDto();
        noticeWithImageDTO.setNotice_id(id);
        noticeWithImageDTO.setHeading(heading);
        noticeWithImageDTO.setDescription(body);
        noticeWithImageDTO.setStartDate(startDate);
        noticeWithImageDTO.setExpiryDate(expiryDate);
        if(noticeEntity.getNotice_image() != null){
            String filePath = noticeEntity.getNotice_image();
            image = Files.readAllBytes(new File(filePath).toPath());
            noticeWithImageDTO.setImage(image);
        }
        else{
            noticeWithImageDTO.setImage(null);
        }
        noticeWithImageDTO.setStatus(noticeEntity.isStatus());
        return noticeWithImageDTO;
    }
}
