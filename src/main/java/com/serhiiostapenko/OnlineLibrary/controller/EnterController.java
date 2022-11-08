package com.serhiiostapenko.OnlineLibrary.controller;

import com.serhiiostapenko.OnlineLibrary.dto.UserSignupDto;
import com.serhiiostapenko.OnlineLibrary.entity.ERole;
import com.serhiiostapenko.OnlineLibrary.entity.User;
import com.serhiiostapenko.OnlineLibrary.service.UserService;
import com.serhiiostapenko.OnlineLibrary.util.UserAccessValidator;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/auth")
public class EnterController {
    private final UserService userService;
    private final UserAccessValidator userAccessValidator;

    @Autowired
    public EnterController(UserService userService, UserAccessValidator userAccessValidator) {
        this.userService = userService;
        this.userAccessValidator = userAccessValidator;
    }

    @GetMapping("")
    public String index() {
        log.info("Forwarding to /auth/");
        return "enter/index";
    }

    @GetMapping("/login")
    public String getLogin() {
        log.info("Forwarding to /auth/login");
        return "enter/login";
    }

    @GetMapping("/signup")
    public String getSignup(@ModelAttribute("userDto") UserSignupDto userSignupDto) {
        log.info("Forwarding to /auth/signup");
        return "enter/signup";
    }

    @PostMapping("/signup")
    public String postSignup(@ModelAttribute("userDto") @Valid UserSignupDto userSignupDto, BindingResult bindingResult) {
        userAccessValidator.validate(userSignupDto, bindingResult);

        log.info("Forwarding to /auth/signup [POST]");

        if (bindingResult.hasErrors()) {
            log.info("BindingResult has errors");
            log.info("Forwarding to /auth/signup");
            return "enter/signup";
        }

        userService.register(convertToUser(userSignupDto), ERole.ROLE_USER.name());

        log.info("Redirecting to /auth/default");
        return "redirect:/auth/default";
    }

    @GetMapping("/default")
    public String defaultAfterLogin(HttpServletRequest request) {
        if (request.isUserInRole("ROLE_ADMIN")) {
            log.info("Forwarding to /admin/books [ADMIN]");
            return "redirect:/admin/books";
        }
        log.info("Forwarding to /user/main [USER]");
        return "redirect:/user/main";
    }

    private User convertToUser(UserSignupDto userSignupDto){
        return new ModelMapper().map(userSignupDto, User.class);
    }
}
