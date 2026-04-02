package com.smartclass.controller;

import com.smartclass.entity.Attendance;
import com.smartclass.payload.request.MarkAttendanceRequest;
import com.smartclass.payload.response.MessageResponse;
import com.smartclass.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @PostMapping
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<?> markAttendance(@RequestBody MarkAttendanceRequest request) {
        try {
            attendanceService.markAttendance(request);
            return ResponseEntity.ok(new MessageResponse("Attendance marked successfully!"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @GetMapping("/{studentId}")
    @PreAuthorize("hasRole('TEACHER') or hasRole('STUDENT') or hasRole('ADMIN')")
    public ResponseEntity<List<Attendance>> getStudentAttendance(@PathVariable Long studentId) {
        return ResponseEntity.ok(attendanceService.getAttendanceForStudent(studentId));
    }
}
