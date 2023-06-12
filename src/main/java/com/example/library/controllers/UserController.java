package com.example.library.controllers;

import com.example.library.models.Book;
import com.example.library.models.EGrade;
import com.example.library.models.User;
import com.example.library.services.BookService;
import com.example.library.services.FileStorageService;
import com.example.library.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("")
public class UserController {
    private final BookService bookService;
    private final UserService userService;
    private final FileStorageService fileStorageService;

    public UserController(BookService bookService, UserService userService, FileStorageService fileStorageService) {
        this.bookService = bookService;
        this.userService = userService;
        this.fileStorageService = fileStorageService;
    }

    @GetMapping("/main")
    public String getMain(Model model, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        List<Book> books;
        if (user.getGrade() != EGrade.DEFAULT) books = bookService.getAllByGrade(user.getGrade());
        else books = bookService.getAll();
        model.addAttribute("books", books);
        return "/user/main";
    }

    @GetMapping("/books")
    public String books(Model model) {
        List<Book> books = bookService.getAll();
        model.addAttribute("books", books);
        model.addAttribute("grades", EGrade.values());
        return "/user/books";
    }

    @GetMapping("/books/search")
    public String search(@RequestParam("search") String search, @RequestParam("selectedGrade") Integer grade, Model model) {
        List<Book> books = bookService.getAll();
        if (!search.isEmpty()) books = bookService.getAllBySearchParam(search);
        if (grade != EGrade.DEFAULT.ordinal()) {
            books = books.stream()
                    .filter(book -> book.getGrade().ordinal() == grade)
                    .collect(Collectors.toList());
        }
        model.addAttribute("books", books);
        model.addAttribute("grades", EGrade.values());
        return "/user/books";
    }

    @GetMapping("/book/{id}")
    public String book(@PathVariable("id") Integer id, Model model, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        user = userService.get(user.getEmail());

        Book book = bookService.get(id);
        model.addAttribute("book", book);

        if(book.getUsers().contains(user)) model.addAttribute("order", true);
        else model.addAttribute("order", false);

        request.getSession().setAttribute("user", user);
        return "/user/book";
    }

    @GetMapping("/profile")
    public String profile() {
        return "/user/profile";
    }

    @PostMapping("/set_avatar")
    public String setAvatar(@RequestParam("image") MultipartFile file, HttpServletRequest request) throws IOException {
        User user = (User) request.getSession().getAttribute("user");
        user = userService.updateAvatar(user, fileStorageService.storeFileToDisk(file, user));
        request.getSession().setAttribute("user", user);
        return "redirect:/profile";
    }

    @GetMapping("/order/{id}")
    public String order(@PathVariable("id") Integer id, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        user = userService.get(user.getEmail());
        Book book = bookService.get(id);

        if(book.getUsers().contains(user)) bookService.returnBook(user,book);
        else bookService.orderBook(user, book);

        request.getSession().setAttribute("user", user);
        return "redirect:/book/" + id;
    }
}
