package com.serhiiostapenko.OnlineLibrary.repo;

import com.serhiiostapenko.OnlineLibrary.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepo extends JpaRepository<File, Integer> {
}
