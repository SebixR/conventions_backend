package com.example.conventions_backend.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class UserDto {
    Long id;
    String firstName;
    String lastName;
    String email;
}
