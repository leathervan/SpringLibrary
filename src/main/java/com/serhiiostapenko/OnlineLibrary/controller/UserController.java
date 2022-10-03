package com.serhiiostapenko.OnlineLibrary.controller;

import com.serhiiostapenko.OnlineLibrary.dto.BookDto;
import com.serhiiostapenko.OnlineLibrary.entity.Book;
import com.serhiiostapenko.OnlineLibrary.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
        List<BookDto> bookDtos = convertToDto(bookService.getTop());
        model.addAttribute("books", bookDtos);
        return "user/main";
    }

    @Transactional
    @GetMapping("/books")
    public String getBooks(Model model){
        List<BookDto> bookDtos = convertToDto(bookService.getAll());
        model.addAttribute("books", bookDtos);
        return "user/books";
    }

    public List<BookDto> convertToDto(List<Book> books){
        List<BookDto> bookDtos = new ArrayList<>();
        for (Book book : books){
            bookDtos.add(new BookDto(book, bookService.getBookFileByBookId(book.getId())));
        }
        return bookDtos;
    }
}
