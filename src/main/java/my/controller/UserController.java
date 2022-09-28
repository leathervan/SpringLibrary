package my.controller;

import my.dto.BookDto;
import my.entity.Book;
import my.entity.File;
import my.service.BookService;
import my.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    private final BookService bookService;
    private final FileService fileService;

    @Autowired
    public UserController(BookService bookService, FileService fileService) {
        this.bookService = bookService;
        this.fileService = fileService;
    }

    @Transactional
    @GetMapping("/main")
    public String getMain(Model model){
        List<BookDto> bookDtos = convertToDto(bookService.getTop());
        model.addAttribute("books", bookDtos);
        return "user/main.html";
    }

    @Transactional
    @GetMapping("/books")
    public String getBooks(Model model){
        List<BookDto> bookDtos = convertToDto(bookService.getAll());
        model.addAttribute("books", bookDtos);
        return "user/books.html";
    }

    public List<BookDto> convertToDto(List<Book> books){
        List<BookDto> bookDtos = new ArrayList<>();
        for (Book book : books){
            bookDtos.add(new BookDto(book,bookService.getAllGenres(), fileService.get(book.getId())));
        }
        return bookDtos;
    }
}
