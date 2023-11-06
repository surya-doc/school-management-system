package com.project.SchoolManagementSystem.notice.Controller;

import com.project.SchoolManagementSystem.exception.ResourceNotFoundException;
import com.project.SchoolManagementSystem.notice.Dto.NoticeMapper;
import com.project.SchoolManagementSystem.notice.Dto.NoticeResDto;
import com.project.SchoolManagementSystem.notice.Service.Impl.NoticeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@RequestMapping("/notices")
@RestController
@CrossOrigin("*")
public class NoticeController {
    private NoticeServiceImpl noticeService;
    private NoticeMapper noticeMapper;

    @Autowired
    public NoticeController(NoticeServiceImpl noticeService, NoticeMapper noticeMapper){
        this.noticeService=noticeService;
        this.noticeMapper=noticeMapper;
    }

    @GetMapping
    public ResponseEntity<List<NoticeResDto>> getAllNotice(@RequestParam(name = "pageno") int pageNo) throws ResourceNotFoundException {
        List<NoticeResDto> allNotices = this.noticeService.getAllNotices(pageNo).getBody();
        return ResponseEntity.ok(allNotices);
    }

    @GetMapping("/active")
    public ResponseEntity<List<NoticeResDto>> getActiveNotices() throws ResourceNotFoundException {
        List<NoticeResDto> allNotices = this.noticeService.getActiveNotices().getBody();
        return ResponseEntity.ok(allNotices);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoticeResDto> getNoticeById(@PathVariable Integer id) throws IOException, ResourceNotFoundException {
        NoticeResDto noticeWithImageDTO = this.noticeService.getNoticeById(id).getBody();
        return ResponseEntity.ok(noticeWithImageDTO);
    }

    @Secured("ROLE_admin")
    @PostMapping
    public ResponseEntity<Object> createNotice(
            @RequestParam("heading") String heading,
            @RequestParam("description") String description,
            @RequestParam("startDate") String startDate,
            @RequestParam("expiryDate") String expiryDate,
            @RequestParam(value = "file",required = false) MultipartFile file
    ) throws IOException, ResourceNotFoundException {

        this.noticeService.createNotice(heading, description, startDate, expiryDate, file);

        return ResponseEntity.ok(Collections.singletonMap("message", "File uploaded successfully"));
    }

    @Secured("ROLE_admin")
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateNotice(
            @PathVariable Integer id,
            @RequestParam("heading") String heading,
            @RequestParam("description") String description,
            @RequestParam("startDate") String startDate,
            @RequestParam("expiryDate") String expiryDate,
            @RequestParam(value = "file",required = false) MultipartFile file
    ) throws ResourceNotFoundException, IOException {
        return this.noticeService.updateNoticeById(id, heading, description, startDate, expiryDate, file);
    }

    @Secured("ROLE_admin")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteNoticeById(@PathVariable Integer id) throws ResourceNotFoundException {
        return this.noticeService.deleteNoticeById(id);
    }
}
