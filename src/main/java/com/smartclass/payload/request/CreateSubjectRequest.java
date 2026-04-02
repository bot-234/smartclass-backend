package com.smartclass.payload.request;

import lombok.Data;

@Data
public class CreateSubjectRequest {
    private String subjectName;
    private Long teacherId;
    private String branch;
    private String year;
}
