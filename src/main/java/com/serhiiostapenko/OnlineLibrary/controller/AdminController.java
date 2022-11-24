package com.serhiiostapenko.OnlineLibrary.controller;


import com.serhiiostapenko.OnlineLibrary.entity.Book;
import com.serhiiostapenko.OnlineLibrary.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
@Slf4j
@Controller
@RequestMapping("/admin")
public class AdminController {
    @Value("${upload.path}")
    private String uploadPath;
    private final BookService bookService;

    @Autowired
    public AdminController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public String getBooks(Model model) {
        log.info("Forwarded to /admin/books");

        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);

        return "admin/books";
    }

    @GetMapping("/edit/{id}")
    public String getEditBook(@PathVariable("id") int id, Model model) {
        log.info("Forwarded to /admin/edit/" + id);

        model.addAttribute("book", bookService.getBook(id));
        return "admin/edit";
    }

    @PostMapping("/edit/{id}")
    public String postEditBook(@PathVariable("id") int id, @ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        log.info("Updating book with id: " + id);
        if (bindingResult.hasErrors()) {
            log.error("Bindingresult has errors");
            return "admin/edit";
        }
        book.setId(id);
        bookService.updateBook(book);
        return "redirect:/admin/books";
    }

    @GetMapping("/add")
    public String getAddBook(@ModelAttribute("book") Book book) {
        log.info("Forwarded to /admin/add");
        return "admin/add";
    }
    @PostMapping("/add")
    public String postAddBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        log.info("Saving book");
        if (bindingResult.hasErrors()) {
            log.error("Bindingresult has errors");
            return "admin/add";
        }
        bookService.saveBook(book);
        return "redirect:/admin/books";
    }

    @GetMapping("/delete/{id}")
    public String getDeleteBook(@PathVariable("id") int id) {
        log.info("Deleting book with id: " + id);
        try {
            Files.deleteIfExists(new File(uploadPath + bookService.getImageFileById(id).getName()).toPath());
            Files.deleteIfExists(new File(uploadPath + bookService.getTxtFileById(id).getName()).toPath());
        } catch (NullPointerException | IOException e){
            log.error(e.getMessage());
        }
        bookService.deleteBook(id);
        return "redirect:/admin/books";
    }
}
