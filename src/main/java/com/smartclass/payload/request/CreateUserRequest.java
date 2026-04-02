package com.smartclass.payload.request;

import lombok.Data;

@Data
public class CreateUserRequest {
    private String name;
    private String email;
    private String role; // "TEACHER" or "STUDENT"
}
