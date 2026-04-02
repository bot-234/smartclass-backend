package com.smartclass.service;

import com.smartclass.entity.Subject;
import com.smartclass.entity.User;
import com.smartclass.payload.request.CreateSubjectRequest;
import com.smartclass.repository.SubjectRepository;
import com.smartclass.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private UserRepository userRepository;

    public Subject createSubject(CreateSubjectRequest request) {
        User teacher = userRepository.findById(request.getTeacherId())
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        if (!"TEACHER".equals(teacher.getRole().name())) {
            throw new RuntimeException("Assigned user is not a teacher");
        }

        Subject subject = new Subject();
        subject.setSubjectName(request.getSubjectName());
        subject.setTeacher(teacher);
        subject.setBranch(request.getBranch());
        subject.setYear(request.getYear());

        return subjectRepository.save(subject);
    }

    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }
}
