package com.example.library.repos;

import com.example.library.models.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileEntityRepo extends JpaRepository<FileEntity, Integer> {
}
