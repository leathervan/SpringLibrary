package com.serhiiostapenko.OnlineLibrary.controller;

import com.serhiiostapenko.OnlineLibrary.entity.BookFile;
import com.serhiiostapenko.OnlineLibrary.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Controller
public class FileController {
    private static final String LOAD_PATH = "src/main/resources/static/upload/";
    private static final String IMG_PATH = "src/main/resources/static/img/";
    private final BookService bookService;

    @Autowired
    public FileController(BookService bookService) {
        this.bookService = bookService;
    }

    @ResponseBody
    @RequestMapping(value = "/book_image/{id}")
    public void getImage(@PathVariable(value = "id") int id, HttpServletResponse response) throws IOException {
        response.setContentType("image/jpeg");
        BookFile bookFile = bookService.getBookFileByBookId(id);
        FileInputStream fis = new FileInputStream(LOAD_PATH + bookFile.getBook().getId() + File.separator + bookFile.getBook().getId() +".jpg");
        fis.transferTo(response.getOutputStream());
    }

    @ResponseBody
    @RequestMapping(value = "/image/{name}")
    public void getImage(@PathVariable(value = "name") String name, HttpServletResponse response) throws IOException {
        response.setContentType("image/jpeg");
        FileInputStream fis = new FileInputStream(IMG_PATH + name +".jpg");
        fis.transferTo(response.getOutputStream());
    }

    @PostMapping("/file/save/{id}")
    public String saveBookFile(@PathVariable("id") int id, @RequestParam("image") MultipartFile multipartFile) throws IOException {
        BookFile bookFile = bookService.getBookFileByBookId(id);

        if(bookFile == null) {
            bookFile = new BookFile();
            bookFile.setBook(bookService.get(id));
        }

        String fileName = getFileName(multipartFile,bookFile);
        if(fileName.endsWith(".jpg")) bookFile.setPicture(fileName);
        if(fileName.endsWith(".txt")) bookFile.setText(fileName);
        bookFile = bookService.save(bookFile);

        Path uploadDir = Paths.get(LOAD_PATH + bookFile.getBook().getId());
        saveFile(uploadDir, fileName, multipartFile);

        return "redirect:/admin/edit/" + id;
    }

    private static void saveFile(Path uploadDir, String fileName, MultipartFile multipartFile) throws IOException {
        if (!Files.exists(uploadDir)) Files.createDirectories(uploadDir);

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadDir.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save image file: " + fileName, ioe);
        }
    }
    private String getFileName(MultipartFile multipartFile, BookFile bookFile){
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        return bookFile.getBook().getId().toString() + fileName.substring(fileName.lastIndexOf('.'));
    }
}
