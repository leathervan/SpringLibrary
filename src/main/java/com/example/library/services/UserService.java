package com.example.library.services;

import com.example.library.models.*;
import com.example.library.repos.FileEntityRepo;
import com.example.library.repos.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
public class UserService{
    private final UserRepo userRepo;
    private final FileEntityRepo fileEntityRepo;
    private final FileStorageService fileStorageService;

    @Autowired
    public UserService(UserRepo userRepo, FileEntityRepo fileEntityRepo, FileStorageService fileStorageService) {
        this.userRepo = userRepo;
        this.fileEntityRepo = fileEntityRepo;
        this.fileStorageService = fileStorageService;
    }

    public List<User> getAll() {
        return userRepo.findAll();
    }

    public User get(String email) {
        log.info("Trying get user by email: " + email);
        Optional<User> user = userRepo.getUserByEmail(email);
        return user.orElse(null);
    }

    public User get(Integer id) {
        Optional<User> user = userRepo.findById(id);
        return user.orElse(null);
    }

    @Transactional
    public User save(User user) {
        user.setRole(ERole.USER);
        FileEntity file = fileEntityRepo.save(new FileEntity());
        user.setAvatar(file);
        log.info("Saved user: " + user);
        return userRepo.save(user);
    }

    @Transactional
    public User updateAvatar(User user, FileEntity fileEntity) {
        User newUser = userRepo.getUserByEmail(user.getEmail()).orElse(null);
        FileEntity file = newUser.getAvatar();

        file.setPath(fileEntity.getPath());
        file.setName(fileEntity.getName());
        file = fileEntityRepo.save(file);

        newUser.setAvatar(file);
        return userRepo.save(newUser);
    }

    @Transactional
    public void delete(User user) throws IOException {
        List<Book> toRemove = new ArrayList<>();
        for (Book book : user.getBooks()) {
            toRemove.add(book);
        }
        for (Book book : toRemove) {
            user.removeBook(book);
        }
        fileStorageService.deleteFileFromDisk(user.getAvatar());
        userRepo.delete(user);
    }
}
