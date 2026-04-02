package com.smartclass.controller;

import com.smartclass.entity.Notice;
import com.smartclass.payload.request.CreateNoticeRequest;
import com.smartclass.payload.response.MessageResponse;
import com.smartclass.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/notices")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER')")
    public ResponseEntity<?> createNotice(@RequestBody CreateNoticeRequest request, Authentication authentication) {
        try {
            noticeService.createNotice(request, authentication.getName());
            return ResponseEntity.ok(new MessageResponse("Notice posted successfully!"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<List<Notice>> getAllNotices() {
        return ResponseEntity.ok(noticeService.getAllNotices());
    }
}
