package com.smartclass.service;

import com.smartclass.entity.Attendance;
import com.smartclass.entity.Subject;
import com.smartclass.entity.User;
import com.smartclass.payload.request.MarkAttendanceRequest;
import com.smartclass.repository.AttendanceRepository;
import com.smartclass.repository.SubjectRepository;
import com.smartclass.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    public Attendance markAttendance(MarkAttendanceRequest request) {
        User student = userRepository.findById(request.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Subject subject = subjectRepository.findById(request.getSubjectId())
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        if (!"STUDENT".equals(student.getRole().name())) {
            throw new RuntimeException("User is not a student");
        }

        Attendance attendance = new Attendance();
        attendance.setStudent(student);
        attendance.setSubject(subject);
        attendance.setDate(request.getDate());
        attendance.setStatus(request.getStatus());

        return attendanceRepository.save(attendance);
    }

    public List<Attendance> getAttendanceForStudent(Long studentId) {
        return attendanceRepository.findByStudentId(studentId);
    }
}
