package com.smartclass.payload.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String name;
    private String password;
}
