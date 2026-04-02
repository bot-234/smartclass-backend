package com.smartclass.service;

import com.smartclass.entity.Assignment;
import com.smartclass.entity.Subject;
import com.smartclass.payload.request.CreateAssignmentRequest;
import com.smartclass.repository.AssignmentRepository;
import com.smartclass.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssignmentService {

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    public Assignment createAssignment(CreateAssignmentRequest request) {
        Subject subject = subjectRepository.findById(request.getSubjectId())
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        Assignment assignment = new Assignment();
        assignment.setTitle(request.getTitle());
        assignment.setDescription(request.getDescription());
        assignment.setSubject(subject);
        assignment.setDueDate(request.getDueDate());

        return assignmentRepository.save(assignment);
    }

    public List<Assignment> getAssignmentsBySubject(Long subjectId) {
        return assignmentRepository.findBySubjectId(subjectId);
    }

    public List<Assignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }
}
