package com.serhiiostapenko.OnlineLibrary.service;

import com.serhiiostapenko.OnlineLibrary.entity.Book;
import com.serhiiostapenko.OnlineLibrary.entity.BookHasGenre;
import com.serhiiostapenko.OnlineLibrary.entity.Genre;
import com.serhiiostapenko.OnlineLibrary.repo.BookHasGenreRepo;
import com.serhiiostapenko.OnlineLibrary.repo.BookRepo;
import com.serhiiostapenko.OnlineLibrary.repo.GenreRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BookRepo bookRepo;
    private final GenreRepo genreRepo;
    private final BookHasGenreRepo bookHasGenreRepo;

    @Autowired
    public BookService(BookRepo bookRepo, GenreRepo genreRepo, BookHasGenreRepo bookHasGenreRepo) {
        this.bookRepo = bookRepo;
        this.genreRepo = genreRepo;
        this.bookHasGenreRepo = bookHasGenreRepo;
    }
    public List<Book> getAll(){
        return bookRepo.findAll();
    }
    public List<Genre> getAllGenres(){
        return genreRepo.findAll();
    }
    public List<Genre> getAllGenresById(int id){
        return genreRepo.findAllByBookId(id);
    }

    public List<Book> getTop() {
        return bookRepo.findTop12ByOrderByRatingDesc();
    }

    public Book get(int id){
        Optional<Book> book = bookRepo.findById(id);
        return book.orElse(null);
    }
    public Genre getGenre(int id){
        Optional<Genre> genre = genreRepo.findById(id);
        return genre.orElse(null);
    }

    @Transactional
    public void addGenre(BookHasGenre bookHasGenre){
        bookHasGenreRepo.save(bookHasGenre);
    }

    @Transactional
    public Book save(Book book){
        return bookRepo.save(book);
    }
    @Transactional
    public Book update(int id, Book book){
        book.setId(id);
        return bookRepo.save(book);
    }
    @Transactional
    public void delete(int id){
        bookRepo.deleteById(id);
    }

    @Transactional
    public void deleteGenre(int bookId, int genreId) {
        BookHasGenre bookHasGenre = bookHasGenreRepo.findBookHasGenreByBook_IdAndGenre_Id(bookId,genreId);
        bookHasGenreRepo.delete(bookHasGenre);
    }
}
