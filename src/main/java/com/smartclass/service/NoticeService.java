package com.smartclass.service;

import com.smartclass.entity.Notice;
import com.smartclass.entity.User;
import com.smartclass.payload.request.CreateNoticeRequest;
import com.smartclass.repository.NoticeRepository;
import com.smartclass.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeService {

    @Autowired
    private NoticeRepository noticeRepository;

    @Autowired
    private UserRepository userRepository;

    public Notice createNotice(CreateNoticeRequest request, String username) {
        User user = userRepository.findByName(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Notice notice = new Notice();
        notice.setTitle(request.getTitle());
        notice.setDescription(request.getDescription());
        notice.setCreatedBy(user);

        return noticeRepository.save(notice);
    }

    public List<Notice> getAllNotices() {
        return noticeRepository.findAllByOrderByDateDesc();
    }
}
