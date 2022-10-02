package com.serhiiostapenko.OnlineLibrary.repo;

import com.serhiiostapenko.OnlineLibrary.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
    Optional<User> getUserByEmail(String email);
}
