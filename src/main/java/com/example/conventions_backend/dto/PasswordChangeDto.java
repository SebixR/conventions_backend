package com.example.conventions_backend.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class PasswordChangeDto {
    private String oldPassword;
    private String newPassword;
    private String email;
}
