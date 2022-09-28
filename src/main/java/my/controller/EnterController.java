package my.controller;

import my.dto.UserLoginDto;
import my.dto.UserSignupDto;
import my.entity.User;
import my.service.UserService;
import my.util.UserAccessValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
        return "enter/index.html";
    }

    @GetMapping("/login")
    public String getLogin(@ModelAttribute("userDto") UserLoginDto userLoginDto) {
        return "enter/login.html";
    }

    @PostMapping("/login")
    @Transactional//for lazy load
    public String postLogin(HttpServletRequest request, @ModelAttribute("userDto") @Valid UserLoginDto userLoginDto, BindingResult bindingResult) {
        userAccessValidator.validate(userLoginDto, bindingResult);

        if (bindingResult.hasErrors()) {
            return "enter/login.html";
        }

        User user = userService.get(userLoginDto.getEmail());
        putUserToSession(request,user);

        return "redirect:/user/main";
    }

    @GetMapping("/signup")
    public String getSignup(@ModelAttribute("userDto") UserSignupDto userSignupDto) {
        return "enter/signup.html";
    }

    @PostMapping("/signup")
    public String postSignup(HttpServletRequest request, @ModelAttribute("userDto") @Valid UserSignupDto userSignupDto, BindingResult bindingResult) {
        userAccessValidator.validate(userSignupDto, bindingResult);

        if (bindingResult.hasErrors()) {
            return "enter/signup.html";
        }
        User user = userSignupDto.getUser();
        userService.save(user);
        putUserToSession(request,user);
        return "redirect:/user/main";
    }

    public static void putUserToSession(HttpServletRequest request, User user){
        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        session.setAttribute("id",user.getId());
    }
}
