package com.example.library.controllers;

import com.example.library.dto.BookDto;
import com.example.library.models.Book;
import com.example.library.models.User;
import com.example.library.services.BookService;
import com.example.library.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/admin")
public class AdminController {
    private final BookService bookService;
    private final UserService userService;

    public AdminController(BookService bookService, UserService userService) {
        this.bookService = bookService;
        this.userService = userService;
    }

    @GetMapping("/main")
    public String getMain() {
        return "/admin/main";
    }
    @GetMapping("/addbook")
    public String addBookGet(@ModelAttribute("bookDto") BookDto bookDto) {
        return "/admin/addbook";
    }

    @PostMapping("/addbook")
    public String addBookPost(@ModelAttribute("bookDto") BookDto bookDto) {
        Book book = new Book(bookDto.getName(),bookDto.getAuthor(),bookDto.getDescription(),bookDto.getGrade(), bookDto.getAmount());
        bookService.save(book, bookDto.getImage(), bookDto.getFile());
        return "/admin/main";
    }

    @GetMapping("/bookslist")
    public String getBooks(Model model) {
        List<Book> books = bookService.getAll();
        model.addAttribute("books", books);
        return "/admin/books";
    }

    @GetMapping("/deletebook/{id}")
    public String deleteBook(@PathVariable("id") Integer id) {
        bookService.delete(bookService.get(id));
        return "redirect:/admin/bookslist";
    }

    @GetMapping("/editbook/{id}")
    public String editBookGet(@PathVariable("id") Integer id, Model model) {
        Book book = bookService.get(id);
        BookDto bookDto = new BookDto(book.getId(), book.getName(), book.getAuthor(), book.getDescription(), book.getGrade().ordinal(), book.getAmount());
        model.addAttribute("bookDto", bookDto);
        model.addAttribute("image", book.getFiles().get(0).getName());
        model.addAttribute("users", book.getUsers());
        return "/admin/editbook";
    }

    @PostMapping("/editbook/{id}")
    public String editBookPost(@PathVariable("id") Integer id, @ModelAttribute("bookDto") BookDto bookDto) {
        Book book = bookService.get(id);
        bookService.update(book,bookDto);
        return "redirect:/admin/bookslist";
    }

    @GetMapping("/userslist")
    public String usersList(Model model) {
        List<User> users = userService.getAll();
        model.addAttribute("users", users);
        return "/admin/users";
    }

    @GetMapping("/deleteuser/{id}")
    public String deleteUser(@PathVariable("id") Integer id, HttpServletRequest request) throws IOException {
        User user = (User) request.getSession().getAttribute("user");
        if(id != user.getId()){
            userService.delete(userService.get(id));
        }
        return "redirect:/admin/userslist";
    }
}
