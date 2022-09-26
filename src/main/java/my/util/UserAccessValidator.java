package my.util;

import my.dto.UserLoginDto;
import my.dto.UserSignupDto;
import my.entity.User;
import my.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserAccessValidator implements Validator {
    private final UserService userService;

    @Autowired
    public UserAccessValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(User.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (target.getClass() == UserLoginDto.class) {
            UserLoginDto userLoginDto = (UserLoginDto) target;
            User user = userService.get(userLoginDto.getEmail());
            if (user == null) errors.rejectValue("email", "", "User don't exist");
            else if (!user.getPassword().equals(userLoginDto.getPassword()))
                errors.rejectValue("password", "", "Wrong password");
        }

        if (target.getClass() == UserSignupDto.class) {
            UserSignupDto userSignupDto = (UserSignupDto) target;

            if (userService.get(userSignupDto.getEmail()) != null)
                errors.rejectValue("email", "", "This email is already taken");
        }
    }
}
