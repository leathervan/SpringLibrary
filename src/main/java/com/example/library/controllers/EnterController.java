package com.example.library.controllers;

import com.example.library.dto.UserLoginDto;
import com.example.library.dto.UserSignupDto;
import com.example.library.models.ERole;
import com.example.library.models.User;
import com.example.library.services.UserService;
import com.example.library.util.UserAccessValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("")
public class EnterController {
    private final UserService userService;
    private final UserAccessValidator userAccessValidator;

    public EnterController(UserService userService, UserAccessValidator userAccessValidator) {
        this.userService = userService;
        this.userAccessValidator = userAccessValidator;
    }

    @GetMapping("")
    public String index() {
        log.info("Forwarded to /auth/");
        return "enter/index";
    }

    @GetMapping("/auth/login")
    public String getLogin(@ModelAttribute("userDto") UserLoginDto userLoginDto) {
        log.info("Forwarded to /auth/login");
        return "enter/login";
    }
    @PostMapping("/auth/login")
    public String postLogin(HttpServletRequest request, @ModelAttribute("userDto") @Valid UserLoginDto userLoginDto, BindingResult bindingResult) {
        log.info("Forwarded to /auth/login [POST]");

        userAccessValidator.validate(userLoginDto, bindingResult);
        if (bindingResult.hasErrors()) {
            log.info("BindingResult has errors");
            log.info("Forwarding to /auth/login");
            return "enter/login";
        }

        User user = userService.get(userLoginDto.getEmail());
        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        return "redirect:/auth/default";
    }
    @GetMapping("/auth/signup")
    public String getSignup(@ModelAttribute("userDto") UserSignupDto userSignupDto) {
        log.info("Forwarded to /auth/signup");
        return "enter/signup";
    }

    @PostMapping("/auth/signup")
    public String postSignup(HttpServletRequest request, @ModelAttribute("userDto") @Valid UserSignupDto userSignupDto, BindingResult bindingResult) {
        log.info("Forwarded to /auth/signup [POST]");

        userAccessValidator.validate(userSignupDto, bindingResult);
        if (bindingResult.hasErrors()) {
            log.info("BindingResult has errors");
            log.info("Forwarding to /auth/signup");
            return "enter/signup";
        }

        User user = new User(userSignupDto.getEmail(),userSignupDto.getName(), userSignupDto.getSurname(), userSignupDto.getPassword(), userSignupDto.getGrade());
        user = userService.save(user);
        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        return "redirect:/auth/default";
    }

    @GetMapping("/auth/default")
    public String defaultAfterLogin(HttpServletRequest request) {
        log.info("Forwarded to /auth/default");

        User user = (User) request.getSession().getAttribute("user");
        if (user.getRole().equals(ERole.ADMIN)) {
            return "redirect:/admin/main";
        }
        return "redirect:/main";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute("user");
        return "redirect:/";
    }

    @GetMapping("/error")
    public String error() {
        log.info("Forwarded to /error");
        return "error";
    }
}
