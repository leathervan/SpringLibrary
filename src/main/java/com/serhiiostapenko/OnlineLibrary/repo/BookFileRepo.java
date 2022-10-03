package com.serhiiostapenko.OnlineLibrary.repo;

import com.serhiiostapenko.OnlineLibrary.entity.BookFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookFileRepo extends JpaRepository<BookFile, Integer> {
    Optional<BookFile> findBookFileByBook_Id(int id);
}
