package my.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UserLoginDto {
    @NotEmpty(message = "Email shouldn`t be empty")
    @Email(message = "Not valid email")
    private String email;

    @Size(min = 8, max = 30, message = "Password size should be between 8 and 30 symbols")
    private String password;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
