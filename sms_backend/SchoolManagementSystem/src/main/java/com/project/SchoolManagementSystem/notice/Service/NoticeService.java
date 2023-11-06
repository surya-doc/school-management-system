package com.project.SchoolManagementSystem.notice.Service;

import com.project.SchoolManagementSystem.exception.ResourceNotFoundException;
import com.project.SchoolManagementSystem.notice.Dto.NoticeResDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface NoticeService {
    public ResponseEntity<List<NoticeResDto>> getAllNotices(Integer pageNo) throws ResourceNotFoundException;
    public ResponseEntity<List<NoticeResDto>> getActiveNotices() throws ResourceNotFoundException;
    public ResponseEntity<NoticeResDto> getNoticeById(Integer id) throws ResourceNotFoundException, IOException;
    public ResponseEntity<String> createNotice(String heading, String description, String startDate, String expiryDate, MultipartFile file) throws ResourceNotFoundException, IOException;
    ResponseEntity<Object> updateNoticeById(Integer noticeId, String heading, String description, String startDate, String expiryDate, MultipartFile file) throws ResourceNotFoundException, IOException;
    public ResponseEntity<Object> deleteNoticeById(Integer id) throws ResourceNotFoundException;
}
