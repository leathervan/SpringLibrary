package my.controller;

import my.dto.UserLoginDto;
import my.dto.UserSignupDto;
import my.service.UserService;
import my.util.UserLoginValidator;
import my.util.UserSignupValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class EnterController {
    private final UserService userService;
    private final UserSignupValidator userSignupValidator;
    private final UserLoginValidator userLoginValidator;

    @Autowired
    public EnterController(UserService userService, UserSignupValidator userSignupValidator, UserLoginValidator userLoginValidator) {
        this.userService = userService;
        this.userSignupValidator = userSignupValidator;
        this.userLoginValidator = userLoginValidator;
    }

    @GetMapping("/")
    public String index() {
        return "enter/index.html";
    }

    @GetMapping("/login")
    public String getLogin(@ModelAttribute("userDto") UserLoginDto userLoginDto) {
        return "enter/login.html";
    }

    @PostMapping("/login")
    public String postLogin(@ModelAttribute("userDto") @Valid UserLoginDto userLoginDto, BindingResult bindingResult) {
        userLoginValidator.validate(userLoginDto, bindingResult);

        if (bindingResult.hasErrors()) {
            return "enter/login.html";
        }

        return "success.html";//must be redirect:
    }

    @GetMapping("/signup")
    public String getSignup(@ModelAttribute("userDto") UserSignupDto userSignupDto) {
        return "enter/signup.html";
    }

    @PostMapping("/signup")
    public String postSignup(@ModelAttribute("userDto") @Valid UserSignupDto userSignupDto, BindingResult bindingResult) {
        userSignupValidator.validate(userSignupDto, bindingResult);

        if (bindingResult.hasErrors()) {
            return "enter/signup.html";
        }

        userService.add(userSignupDto.getUser());
        return "success.html";//must be redirect:
    }
}
