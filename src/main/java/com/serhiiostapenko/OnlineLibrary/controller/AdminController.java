package com.serhiiostapenko.OnlineLibrary.controller;


import com.serhiiostapenko.OnlineLibrary.entity.Book;
import com.serhiiostapenko.OnlineLibrary.service.BookService;
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
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);

        return "admin/books";
    }

    @GetMapping("/edit/{id}")
    public String getEditBook(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookService.getBook(id));

        return "admin/edit";
    }

    @PostMapping("/edit/{id}")
    public String postEditBook(@PathVariable("id") int id, @ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/edit";
        }
        book.setId(id);
        bookService.updateBook(book);
        return "redirect:/admin/books";
    }

    @GetMapping("/add")
    public String getAddBook(@ModelAttribute("book") Book book) {
        return "admin/add";
    }
    @PostMapping("/add")
    public String postAddBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/add";
        }
        bookService.saveBook(book);
        return "redirect:/admin/books";
    }

    @GetMapping("/delete/{id}")
    public String getDeleteBook(@PathVariable("id") int id) {
        try {
            Files.deleteIfExists(new File(uploadPath + bookService.getImageFileById(id).getName()).toPath());
            Files.deleteIfExists(new File(uploadPath + bookService.getTxtFileById(id).getName()).toPath());
        } catch (NullPointerException | IOException e){
            e.printStackTrace();
        }
        bookService.deleteBook(id);
        return "redirect:/admin/books";
    }
}
