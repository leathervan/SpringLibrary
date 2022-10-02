package com.serhiiostapenko.OnlineLibrary.repo;

import com.serhiiostapenko.OnlineLibrary.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenreRepo extends JpaRepository<Genre, Integer> {
    @Query("select g from Genre g join BookHasGenre bg on g.id=bg.genre.id join Book b on b.id=bg.book.id and b.id = :id")
    List<Genre> findAllByBookId(int id);
}
