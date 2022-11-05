package com.serhiiostapenko.OnlineLibrary.service;

import com.serhiiostapenko.OnlineLibrary.entity.User;
import com.serhiiostapenko.OnlineLibrary.repo.RoleRepo;
import com.serhiiostapenko.OnlineLibrary.repo.UserRepo;
import com.serhiiostapenko.OnlineLibrary.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserService implements UserDetailsService {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepo userRepo, RoleRepo roleRepo,@Lazy PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }
    public User get(String email){
        Optional<User> user = userRepo.getUserByEmail(email);
        return user.orElse(null);
    }

    @Transactional
    public User register(User user, String role){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(roleRepo.getRoleByName(role));
        System.out.println(user);
        return userRepo.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepo.getUserByEmail(username);

        if(user.isEmpty())
            throw new UsernameNotFoundException("User not found");

        return new UserDetailsImpl(user.get());
    }
}
