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
public class UserLoginValidator implements Validator {
    private final UserService userService;

    @Autowired
    public UserLoginValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(User.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserLoginDto userLoginDto = (UserLoginDto) target;
        User user = userService.getUser(userLoginDto.getEmail());
        if (user == null) errors.rejectValue("email","","User don't exist");
        else if(!user.getPassword().equals(userLoginDto.getPassword())) errors.rejectValue("password","","Wrong password");
    }
}
