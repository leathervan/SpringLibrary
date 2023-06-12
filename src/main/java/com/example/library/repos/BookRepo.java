package com.example.library.repos;

import com.example.library.models.Book;
import com.example.library.models.EGrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepo extends JpaRepository<Book, Integer> {

    List<Book> findByNameContaining(String name);

    List<Book> findByAuthorContaining(String author);

    List<Book> findAllByGrade(EGrade grade);
}
