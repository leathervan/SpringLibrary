package my.util;

import my.dto.UserSignupDto;
import my.entity.User;
import my.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
@Component
public class UserSignupValidator implements Validator {
    private final UserService userService;

    @Autowired
    public UserSignupValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(User.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserSignupDto userSignupDto = (UserSignupDto) target;

        if(userService.getUser(userSignupDto.getEmail()) != null) errors.rejectValue("email","","This email is already taken");
    }
}
