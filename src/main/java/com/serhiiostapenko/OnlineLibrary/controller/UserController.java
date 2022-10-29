package com.serhiiostapenko.OnlineLibrary.controller;

import com.serhiiostapenko.OnlineLibrary.entity.Book;
import com.serhiiostapenko.OnlineLibrary.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    private final BookService bookService;

    @Autowired
    public UserController(BookService bookService) {
        this.bookService = bookService;
    }

    @Transactional
    @GetMapping("/main")
    public String getMain(Model model){
        List<Book> books = bookService.getTop12Books();
        model.addAttribute("books", books);
        return "user/main";
    }

    @Transactional
    @GetMapping("/books")
    public String getBooks(Model model){
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "user/books";
    }

    @GetMapping("/books/search")
    public String getFilterBooks(@RequestParam("search") String search, Model model){
        List<Book> books;
        if(search.isEmpty()) books = bookService.getAllBooks();
        else books = bookService.getAllByNameOrAuthor(search);
        model.addAttribute("books", books);
        return "user/books";
    }
    @GetMapping("/book/{id}")
    public String getBook(@PathVariable("id") int id, Model model){
        Book book = bookService.getBook(id);
        model.addAttribute("title", book.getName());
        model.addAttribute("book", book);
        return "user/book";
    }
}
