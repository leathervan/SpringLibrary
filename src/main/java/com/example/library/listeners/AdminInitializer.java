package com.example.library.listeners;

import com.example.library.models.EGrade;
import com.example.library.models.ERole;
import com.example.library.models.User;
import com.example.library.repos.UserRepo;
import com.example.library.services.UserService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class AdminInitializer implements ApplicationListener<ContextRefreshedEvent> {
    private final UserService userService;

    public AdminInitializer(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (userService.findAllByRole(ERole.ADMIN).isEmpty()) {
            User user = new User("dima.dikenko@gmail.com", "Dima", "Dikenko", "dima.dikenko",ERole.ADMIN, EGrade.DEFAULT.ordinal());
            userService.save(user);
        }
    }
}
