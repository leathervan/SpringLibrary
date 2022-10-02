package com.serhiiostapenko.OnlineLibrary.repo;

import com.serhiiostapenko.OnlineLibrary.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<Role, Integer> {
    Role getRoleByName(String name);
}
