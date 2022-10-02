package com.serhiiostapenko.OnlineLibrary.repo;

import com.serhiiostapenko.OnlineLibrary.entity.Book;
import com.serhiiostapenko.OnlineLibrary.entity.BookHasGenre;
import com.serhiiostapenko.OnlineLibrary.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookHasGenreRepo extends JpaRepository<BookHasGenre, Integer> {
    BookHasGenre findBookHasGenreByBook_IdAndGenre_Id(int bookId, int genreId);
}
