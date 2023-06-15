package com.example.library.repos;

import com.example.library.models.ERole;
import com.example.library.models.User;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
    Optional<User> getUserByEmail(String email);
    List<User> findAllByRole(ERole role);
}
