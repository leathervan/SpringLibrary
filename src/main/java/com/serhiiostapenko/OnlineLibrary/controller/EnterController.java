package com.serhiiostapenko.OnlineLibrary.controller;

import com.serhiiostapenko.OnlineLibrary.dto.UserSignupDto;
import com.serhiiostapenko.OnlineLibrary.entity.ERole;
import com.serhiiostapenko.OnlineLibrary.service.UserService;
import com.serhiiostapenko.OnlineLibrary.util.UserAccessValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class EnterController {
    private final UserService userService;
    private final UserAccessValidator userAccessValidator;

    @Autowired
    public EnterController(UserService userService, UserAccessValidator userAccessValidator) {
        this.userService = userService;
        this.userAccessValidator = userAccessValidator;
    }

    @GetMapping("/")
    public String index() {
        return "enter/index";
    }

    @GetMapping("/login")
    public String getLogin() {
        return "enter/login";
    }

    @GetMapping("/signup")
    public String getSignup(@ModelAttribute("userDto") UserSignupDto userSignupDto) {
        return "enter/signup";
    }

    @PostMapping("/signup")
    public String postSignup(@ModelAttribute("userDto") @Valid UserSignupDto userSignupDto, BindingResult bindingResult) {
        userAccessValidator.validate(userSignupDto, bindingResult);

        if (bindingResult.hasErrors()) {
            return "enter/signup";
        }

        userService.register(userSignupDto.getUser(), ERole.ROLE_USER.name());

        return "redirect:/default";
    }

    @GetMapping("/default")
    public String defaultAfterLogin(HttpServletRequest request) {
        if (request.isUserInRole("ROLE_ADMIN")) {
            return "redirect:/admin/books";
        }
        return "redirect:/user/main";
    }
}
