package com.serhiiostapenko.OnlineLibrary.service;

import com.serhiiostapenko.OnlineLibrary.entity.Book;
import com.serhiiostapenko.OnlineLibrary.entity.FileEntity;
import com.serhiiostapenko.OnlineLibrary.repo.BookRepo;
import com.serhiiostapenko.OnlineLibrary.repo.FileRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Slf4j
@Service
@Transactional(readOnly = true)
public class BookService {
    private final BookRepo bookRepo;
    private final FileRepo fileRepo;

    @Autowired
    public BookService(BookRepo bookRepo, FileRepo fileRepo) {
        this.bookRepo = bookRepo;
        this.fileRepo = fileRepo;
    }
    public List<Book> getAllBooks(){
        log.info("Got all books");
        return bookRepo.findAll();
    }
    public List<Book> getAllByNameOrAuthor(String search) {
        log.info("Got all books searched by name or author");
        return bookRepo.findAllByNameOrAuthor(search, search);
    }

    public List<Book> getTop12Books() {
        log.info("Got top 12 books");
        return bookRepo.findTop12ByOrderByRatingDesc();
    }
    public Book getBook(int id){
        log.info("Trying get book by id: " + id);
        Optional<Book> book = bookRepo.findById(id);
        return book.orElse(null);
    }
    public FileEntity getFileByName(String name){
        log.info("Trying get file: " + name);
        Optional<FileEntity> file= fileRepo.findByName(name);
        return file.orElse(null);
    }

    public FileEntity getImageFileById(int id){
        log.info("Trying get image by id: " + id);
        Optional<FileEntity> file= fileRepo.findByBookIdAndAndNameEndingWith(id, ".jpg");
        return file.orElse(null);
    }

    public FileEntity getTxtFileById(int id){
        log.info("Trying get txt by id: " + id);
        Optional<FileEntity> file= fileRepo.findByBookIdAndAndNameEndingWith(id, ".txt");
        return file.orElse(null);
    }

    @Transactional
    public Book saveBook(Book book){
        log.info("Saved book: " + book);
        return bookRepo.save(book);
    }
    @Transactional
    public FileEntity saveFile(FileEntity file){
        log.info("Saved file: " + file);
        return fileRepo.save(file);
    }

    @Transactional
    public Book updateBook(Book book){
        log.info("Updated book: " + book);
        return bookRepo.save(book);
    }
    @Transactional
    public void deleteBook(int id){
        log.info("deleted book with id: " + id);
        bookRepo.deleteById(id);
    }

}
