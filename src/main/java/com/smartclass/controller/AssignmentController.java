package com.smartclass.controller;

import com.smartclass.entity.Assignment;
import com.smartclass.payload.request.CreateAssignmentRequest;
import com.smartclass.payload.response.MessageResponse;
import com.smartclass.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    @PostMapping
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<?> createAssignment(@RequestBody CreateAssignmentRequest request) {
        try {
            assignmentService.createAssignment(request);
            return ResponseEntity.ok(new MessageResponse("Assignment created successfully!"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<List<Assignment>> getAssignments(@RequestParam(required = false) Long subjectId) {
        if (subjectId != null) {
            return ResponseEntity.ok(assignmentService.getAssignmentsBySubject(subjectId));
        }
        return ResponseEntity.ok(assignmentService.getAllAssignments());
    }
}
