package com.serhiiostapenko.OnlineLibrary.repo;

import com.serhiiostapenko.OnlineLibrary.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileRepo extends JpaRepository<FileEntity, Integer> {
    Optional<FileEntity> findByName(String name);

    Optional<FileEntity> findByBookIdAndAndNameEndingWith(int id, String extension);
}
