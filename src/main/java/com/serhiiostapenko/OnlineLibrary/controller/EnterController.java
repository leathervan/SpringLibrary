package com.serhiiostapenko.OnlineLibrary.controller;

import com.serhiiostapenko.OnlineLibrary.dto.UserLoginDto;
import com.serhiiostapenko.OnlineLibrary.dto.UserSignupDto;
import com.serhiiostapenko.OnlineLibrary.entity.Role;
import com.serhiiostapenko.OnlineLibrary.entity.User;
import com.serhiiostapenko.OnlineLibrary.service.UserService;
import com.serhiiostapenko.OnlineLibrary.util.UserAccessValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    public String index(HttpServletRequest request) {
        request.getSession().invalidate();
        return "enter/index";
    }

    @GetMapping("/login")
    public String getLogin(@ModelAttribute("userDto") UserLoginDto userLoginDto) {
        return "enter/login";
    }

    @PostMapping("/login")
    @Transactional//for lazy load
    public String postLogin(HttpServletRequest request, @ModelAttribute("userDto") @Valid UserLoginDto userLoginDto, BindingResult bindingResult) {
        userAccessValidator.validate(userLoginDto, bindingResult);

        if (bindingResult.hasErrors()) {
            return "enter/login";
        }

        User user = userService.get(userLoginDto.getEmail());
        putUserToSession(request, user);

        String loadedPage = checkRole(user);
        return loadedPage;
    }

    @GetMapping("/signup")
    public String getSignup(@ModelAttribute("userDto") UserSignupDto userSignupDto) {
        return "enter/signup";
    }

    @PostMapping("/signup")
    public String postSignup(HttpServletRequest request, @ModelAttribute("userDto") @Valid UserSignupDto userSignupDto, BindingResult bindingResult) {
        userAccessValidator.validate(userSignupDto, bindingResult);

        if (bindingResult.hasErrors()) {
            return "enter/signup";
        }
        User user = userSignupDto.getUser();
        userService.save(user);
        putUserToSession(request, user);

        String loadedPage = checkRole(user);
        return loadedPage;
    }

    @GetMapping("/logout")
    public String getLogout(HttpServletRequest request) {
        request.getSession().removeAttribute("user");
        return "redirect:/";
    }

    private static void putUserToSession(HttpServletRequest request, User user) {
        HttpSession session = request.getSession();
        session.setAttribute("user", user);
    }

    private static String checkRole(User user) {
        Role role = user.getRole();
        String LOADED_PAGE;
        switch (role.getName()) {
            case "admin":
                LOADED_PAGE = "/admin/books";
                break;
            case "user":
                LOADED_PAGE = "/user/main";
                break;
            default:
                LOADED_PAGE = "/";
                break;
        }
        return "redirect:" + LOADED_PAGE;
    }
}
