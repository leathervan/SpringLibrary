package com.serhiiostapenko.OnlineLibrary.dto;


import com.serhiiostapenko.OnlineLibrary.entity.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UserSignupDto {
    @NotEmpty(message = "Email shouldn`t be empty")
    @Email(message = "Not valid email")
    private String email;

    @Size(min = 8, max = 30, message = "Password size should be between 8 and 30 symbols")
    private String password;
    @NotEmpty(message = "Username shouldn`t be empty")
    private String username;

    public User getUser() {
        return new User(this.email, this.username, this.password);
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
