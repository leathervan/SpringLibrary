package com.example.library.services;

import com.example.library.dto.BookDto;
import com.example.library.models.Book;
import com.example.library.models.EGrade;
import com.example.library.models.FileEntity;
import com.example.library.models.User;
import com.example.library.repos.BookRepo;
import com.example.library.repos.UserRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BookRepo bookRepo;
    private final UserRepo userRepo;
    private final FileStorageService fileStorageService;

    public BookService(BookRepo bookRepo, UserRepo userRepo, FileStorageService fileStorageService) {
        this.bookRepo = bookRepo;
        this.userRepo = userRepo;
        this.fileStorageService = fileStorageService;
    }


    public Book get(Integer id) {
        Optional<Book> book = bookRepo.findById(id);
        return book.orElse(null);
    }

    public List<Book> getAll() {
        return bookRepo.findAll();
    }

    public List<Book> getAllBySearchParam(String search) {
        List<Book> books = new ArrayList<>();
        books.addAll(bookRepo.findByNameContaining(search));
        books.addAll(bookRepo.findByAuthorContaining(search));
        return books;
    }

    public List<Book> getAllByGrade(EGrade grade) {
        return bookRepo.findAllByGrade(grade);
    }

    @Transactional
    public Book save(Book book, MultipartFile image, MultipartFile file) {
        try {
            book = bookRepo.save(book);
            book.setFile(fileStorageService.storeFileToDisk(image, book));
            book.setFile(fileStorageService.storeFileToDisk(file, book));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return bookRepo.save(book);
    }

    @Transactional
    public Book update(Book book, BookDto bookDto) {
        try {
            if (bookDto.getName() != null) book.setName(bookDto.getName());
            if (bookDto.getDescription() != null) book.setDescription(bookDto.getDescription());
            if (bookDto.getAuthor() != null) book.setAuthor(bookDto.getAuthor());
            if (bookDto.getGrade() != null) book.setGrade(EGrade.values()[bookDto.getGrade()]);
            if (bookDto.getAmount() != null) book.setAmount(bookDto.getAmount());
            if (!bookDto.getImage().isEmpty()) {
                fileStorageService.deleteFileFromDisk(book.getFiles().get(0));
                book.setFile(fileStorageService.storeFileToDisk(bookDto.getImage(), book));
            }
            if (!bookDto.getFile().isEmpty()) {
                fileStorageService.deleteFileFromDisk(book.getFiles().get(1));
                book.setFile(fileStorageService.storeFileToDisk(bookDto.getFile(), book));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return bookRepo.save(book);
    }

    @Transactional
    public void delete(Book book) {
        try {
            for (FileEntity file : book.getFiles()) {
                fileStorageService.deleteFileFromDisk(file);
            }
            List<User> toRemove = new ArrayList<>();
            for (User user : book.getUsers()) {
                toRemove.add(user);
            }
            for (User user : toRemove) {
                book.removeUser(user);
            }
            bookRepo.delete(book);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public void orderBook(User user, Book book) {
        if (book.getUsers().contains(user)) return;

        book.addUser(user);
        book.setAmount(book.getAmount()-1);

        bookRepo.save(book);
        userRepo.save(user);
    }

    @Transactional
    public void returnBook(User user, Book book) {
        book.removeUser(user);
        book.setAmount(book.getAmount()+1);

        bookRepo.save(book);
        userRepo.save(user);
    }
}
