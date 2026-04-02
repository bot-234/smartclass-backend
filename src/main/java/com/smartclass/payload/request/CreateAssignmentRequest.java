package com.smartclass.payload.request;

import lombok.Data;
import java.time.LocalDate;

@Data
public class CreateAssignmentRequest {
    private String title;
    private String description;
    private Long subjectId;
    private LocalDate dueDate;
}
