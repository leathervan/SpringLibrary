package com.example.library.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserLoginDto {
    @NotEmpty(message = "Email shouldn`t be empty")
    @Email(message = "Not valid email")
    private String email;

    @Size(min = 8, max = 30, message = "Password size should be between 8 and 30 symbols")
    private String password;
}
