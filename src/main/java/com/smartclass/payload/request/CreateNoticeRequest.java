package com.smartclass.payload.request;

import lombok.Data;

@Data
public class CreateNoticeRequest {
    private String title;
    private String description;
}
