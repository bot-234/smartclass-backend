package com.smartclass.payload.request;

import lombok.Data;
import java.time.LocalDate;

@Data
public class MarkAttendanceRequest {
    private Long studentId;
    private Long subjectId;
    private LocalDate date;
    private String status; // Present or Absent
}
