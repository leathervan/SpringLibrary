package com.serhiiostapenko.OnlineLibrary.dto;


import com.serhiiostapenko.OnlineLibrary.entity.User;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
@Data
public class UserSignupDto {
    @NotEmpty(message = "Email shouldn`t be empty")
    @Email(message = "Not valid email")
    private String email;

    @NotEmpty(message = "Username shouldn`t be empty")
    private String username;

    @Size(min = 8, max = 30, message = "Password size should be between 8 and 30 symbols")
    private String password;

    public User getUser() {
        return new User(email,username,password);
    }
}
