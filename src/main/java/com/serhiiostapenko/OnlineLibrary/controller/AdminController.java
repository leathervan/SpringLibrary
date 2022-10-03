package com.serhiiostapenko.OnlineLibrary.controller;


import com.serhiiostapenko.OnlineLibrary.dto.BookDto;
import com.serhiiostapenko.OnlineLibrary.entity.Book;
import com.serhiiostapenko.OnlineLibrary.entity.BookHasGenre;
import com.serhiiostapenko.OnlineLibrary.entity.Genre;
import com.serhiiostapenko.OnlineLibrary.service.BookService;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final BookService bookService;

    @Autowired
    public AdminController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public String getBooks(Model model) {
        List<Book> books = bookService.getAll();
        model.addAttribute("books", books);

        return "admin/books";
    }

    @GetMapping("/edit/{id}")
    public String getEditBook(@PathVariable("id") int id, Model model) {
        List<Genre> bookGenres = bookService.getAllGenresById(id);
        List<Genre> allGenres = bookService.getAllGenres();
        allGenres.removeAll(bookGenres);
        model.addAttribute("book", bookService.get(id));
        model.addAttribute("bookGenres", bookGenres);
        model.addAttribute("bookDto", new BookDto());
        model.addAttribute("allGenres", allGenres);

        return "admin/edit";
    }

    @PostMapping("/edit/{id}")
    public String postEditBook(@PathVariable("id") int id, @ModelAttribute("bookDto") BookDto bookDto) {
        Book book = bookService.get(id);
        bookDto.updateBook(book);
        bookService.update(id, book);
        return "redirect:/admin/books";
    }

    @PostMapping("/add_genre/{bookId}/{genreId}")
    public String postAddGenre(@PathVariable("bookId") int bookId, @PathVariable("genreId") int genreId) {
        bookService.addGenre(new BookHasGenre(bookService.get(bookId), bookService.getGenre(genreId)));
        return "redirect:/admin/edit/" + bookId;
    }

    @PostMapping("/delete_genre/{bookId}/{genreId}")
    public String postDeleteGenre(@PathVariable("bookId") int bookId, @PathVariable("genreId") int genreId) {
        bookService.deleteGenre(bookId, genreId);
        return "redirect:/admin/edit/" + bookId;
    }

    @GetMapping("/add")
    public String getAddBook(@ModelAttribute("bookDto") BookDto bookDto) {
        return "admin/add";
    }
    @PostMapping("/add")
    public String postAddBook(@ModelAttribute("bookDto") BookDto bookDto) {
        bookService.save(bookDto.getBook());
        return "redirect:/admin/books";
    }

    @GetMapping("/delete/{id}")
    public String getDeleteBook(@PathVariable("id") int id) throws IOException {
        bookService.delete(id);
        FileUtils.deleteDirectory(new File("src/main/resources/static/upload/" + id));
        return "redirect:/admin/books";
    }
}
