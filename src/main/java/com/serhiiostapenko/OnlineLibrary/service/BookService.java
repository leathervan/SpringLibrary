package com.serhiiostapenko.OnlineLibrary.service;

import com.serhiiostapenko.OnlineLibrary.entity.Book;
import com.serhiiostapenko.OnlineLibrary.entity.FileEntity;
import com.serhiiostapenko.OnlineLibrary.repo.BookRepo;
import com.serhiiostapenko.OnlineLibrary.repo.FileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
        return bookRepo.findAll();
    }
    public List<Book> getAllByNameOrAuthor(String search) {return bookRepo.findAllByNameOrAuthor(search, search);}

    public List<Book> getTop12Books() {
        return bookRepo.findTop12ByOrderByRatingDesc();
    }
    public Book getBook(int id){
        Optional<Book> book = bookRepo.findById(id);
        return book.orElse(null);
    }
    public FileEntity getFileByName(String name){
        Optional<FileEntity> file= fileRepo.findByName(name);
        return file.orElse(null);
    }

    public FileEntity getImageFileById(int id){
        Optional<FileEntity> file= fileRepo.findByBookIdAndAndNameEndingWith(id, ".jpg");
        return file.orElse(null);
    }

    public FileEntity getTxtFileById(int id){
        Optional<FileEntity> file= fileRepo.findByBookIdAndAndNameEndingWith(id, ".txt");
        return file.orElse(null);
    }

    @Transactional
    public Book saveBook(Book book){
        return bookRepo.save(book);
    }
    @Transactional
    public FileEntity saveFile(FileEntity file){
        return fileRepo.save(file);
    }

    @Transactional
    public Book updateBook(Book book){
        return bookRepo.save(book);
    }
    @Transactional
    public void deleteBook(int id){
        bookRepo.deleteById(id);
    }

}
