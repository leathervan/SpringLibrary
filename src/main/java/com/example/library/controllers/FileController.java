package com.example.library.controllers;

import com.example.library.models.Book;
import com.example.library.models.FileEntity;
import com.example.library.services.BookService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

@Slf4j
@Controller
public class FileController {
    @Value("${image.path}")
    private String imagePath;

    @Value("${upload.path}")
    private String uploadPath;

    private final String notFound = "not_found.jpg";

    private final BookService bookService;

    public FileController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/image/{name}")
    public @ResponseBody void getImage(@PathVariable("name") String name, HttpServletResponse response) throws IOException, NullPointerException {
        response.setContentType("image/jpeg");
        File file = new File(imagePath + name);
        if (!file.exists()) name = notFound;

        InputStream in = new FileInputStream(imagePath + name);
        IOUtils.copy(in, response.getOutputStream());
        in.close();
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> download(@PathVariable("id") int id) throws IOException {
        Book book = bookService.get(id);
        FileEntity fileEntity = book.getFiles().get(1);
        File downloadFile = new File(fileEntity.getPath() + fileEntity.getName());
        if (!downloadFile.exists()) return ResponseEntity.noContent().build();

        String encodedFileName = URLEncoder.encode(downloadFile.getName());
        InputStreamResource resource = new InputStreamResource(new FileInputStream(downloadFile));
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + encodedFileName);

        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }
}
