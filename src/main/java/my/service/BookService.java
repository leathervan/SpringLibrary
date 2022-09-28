package my.service;

import my.entity.Book;
import my.entity.File;
import my.entity.Genre;
import my.repo.BookRepo;
import my.repo.FileRepo;
import my.repo.GenreRepo;
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

    @Autowired
    public BookService(BookRepo bookRepo, GenreRepo genreRepo) {
        this.bookRepo = bookRepo;
        this.genreRepo = genreRepo;
    }
    public List<Book> getAll(){
        return bookRepo.findAll();
    }
    public List<Genre> getAllGenres(){
        return genreRepo.findAll();
    }

    public List<Book> getTop() {
        return bookRepo.findTop12ByOrderByRatingDesc();
    }

    public Book get(int id){
        Optional<Book> book = bookRepo.findById(id);
        return book.orElse(null);
    }

    @Transactional
    public Book save(Book book){
        return bookRepo.save(book);
    }
    @Transactional
    public Book update(int id, Book user){
        user.setId(id);
        return bookRepo.save(user);
    }
    @Transactional
    public void delete(int id){
        bookRepo.deleteById(id);
    }

}
