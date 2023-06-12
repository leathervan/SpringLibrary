package com.example.library.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserSignupDto {
    @NotEmpty(message = "Email shouldn`t be empty")
    @Email(message = "Not valid email")
    private String email;

    @NotEmpty(message = "Name shouldn`t be empty")
    private String name;

    @NotEmpty(message = "Surname shouldn`t be empty")
    private String surname;

    @Size(min = 8, max = 30, message = "Password size should be between 8 and 30 symbols")
    private String password;
    private Integer grade;
}
