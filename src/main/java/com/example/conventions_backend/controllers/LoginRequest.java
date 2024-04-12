package com.example.conventions_backend.controllers;

import lombok.*;

@Data
@Builder
@Getter
@AllArgsConstructor
public class LoginRequest {
    private String email;
    private String password;


}
