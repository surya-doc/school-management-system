package com.project.SchoolManagementSystem.notice.Service.Impl;

import com.project.SchoolManagementSystem.exception.ResourceNotFoundException;
import com.project.SchoolManagementSystem.notice.Dao.NoticeDao;
import com.project.SchoolManagementSystem.notice.Dto.NoticeDto;
import com.project.SchoolManagementSystem.notice.Dto.NoticeMapper;
import com.project.SchoolManagementSystem.notice.Dto.NoticeResDto;
import com.project.SchoolManagementSystem.notice.Model.NoticeEntity;
import com.project.SchoolManagementSystem.notice.Service.NoticeService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class NoticeServiceImpl implements NoticeService {

    private NoticeDao noticeDao;
    private NoticeMapper noticeMapper;
    @Autowired
    public NoticeServiceImpl(NoticeDao noticeDao, NoticeMapper noticeMapper){
        this.noticeDao=noticeDao;
        this.noticeMapper=noticeMapper;
    };




    @Override
    public ResponseEntity<List<NoticeResDto>> getAllNotices(Integer pageNo) throws ResourceNotFoundException {
        Pageable pageRequest = PageRequest.of(pageNo, 10);
        Page<NoticeEntity> pageOfNotices = this.noticeDao.findAll(pageRequest);
        List<NoticeEntity> noticeList = pageOfNotices.getContent();
        List<NoticeResDto> notices = (Objects.requireNonNull(noticeList).stream().map((e)-> {
            try {
                return noticeMapper.convertToNoticeResDto(e);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }).collect(Collectors.toList()));
        return ResponseEntity.ok(notices);
    }

    @Override
    public ResponseEntity<List<NoticeResDto>> getActiveNotices() throws ResourceNotFoundException {
        List<NoticeEntity> noticeList = this.noticeDao.findAll();
        List<NoticeResDto> notices = (Objects.requireNonNull(noticeList).stream().filter(NoticeEntity::isStatus).map((e)-> {
            try {
                return noticeMapper.convertToNoticeResDto(e);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }).collect(Collectors.toList()));
        return ResponseEntity.ok(notices);
    }

    @Override
    public ResponseEntity<NoticeResDto> getNoticeById(Integer id) throws ResourceNotFoundException, IOException {
        NoticeEntity notice = this.noticeDao.findById(id).orElseThrow(()->new ResourceNotFoundException("No notice with this id" + id));
        NoticeResDto noticeResDto = this.noticeMapper.convertToNoticeResDto(notice);
        return ResponseEntity.ok(noticeResDto);
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public ResponseEntity<String> createNotice(String heading, String description, String startDate, String expiryDate, MultipartFile file) throws ResourceNotFoundException, IOException {
        String filePath = null;
        if(file != null){
            filePath = "/home/surya/Desktop/sms/images" + File.separator + file.getOriginalFilename();
            file.transferTo(new File(filePath));
        }

        NoticeDto entity = new NoticeDto();
        entity.setHeading(heading);
        entity.setDescription(description);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localStDate = LocalDate.parse(startDate, formatter);
        Date dateStart = Date.from(localStDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        entity.setStartDate(dateStart);

        LocalDate localEdDate = LocalDate.parse(expiryDate, formatter);
        Date dateEnd = Date.from(localEdDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        entity.setExpiryDate(dateEnd);
        entity.setImage(filePath);

        NoticeEntity noticeEntity = this.noticeMapper.convertToEntity(entity);
        noticeDao.save(noticeEntity);
        return ResponseEntity.ok("created notice");
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public ResponseEntity<Object> updateNoticeById(Integer noticeId, String heading, String description, String startDate, String expiryDate, MultipartFile file) throws ResourceNotFoundException, IOException {
        String filePath = "/home/surya/Desktop/sms/images" + File.separator + file.getOriginalFilename();
        file.transferTo(new File(filePath));

        Optional<NoticeEntity> entity = this.noticeDao.findById(noticeId);
        if(entity.isEmpty()){
            throw new ResourceNotFoundException("Notice doesnot present with this id: "+noticeId);
        }

        NoticeEntity noticeEntity = entity.get();
        noticeEntity.setHeading(heading);
        noticeEntity.setDescription(description);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localStDate = LocalDate.parse(startDate, formatter);
        Date dateStart = Date.from(localStDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        noticeEntity.setStart_date(dateStart);

        LocalDate localEdDate = LocalDate.parse(expiryDate, formatter);
        Date dateEnd = Date.from(localEdDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        noticeEntity.setExpiry_date(dateEnd);
        noticeEntity.setNotice_image(filePath);

        noticeDao.save(noticeEntity);
        return ResponseEntity.ok(Collections.singletonMap("message", "updated notice"));
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public ResponseEntity<Object> deleteNoticeById(Integer id) throws ResourceNotFoundException {
        NoticeEntity notice = this.noticeDao.findById(id).orElseThrow(()->new ResourceNotFoundException("No notice with this id" + id));
        notice.setStatus(false);
        this.noticeDao.save(notice);
        return ResponseEntity.ok(Collections.singletonMap("message", "Notice deleted."));
    }
}
