package com.serhiiostapenko.OnlineLibrary.controller;


import com.serhiiostapenko.OnlineLibrary.entity.FileEntity;
import com.serhiiostapenko.OnlineLibrary.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Slf4j
@Controller
public class FileController {
    @Value("${upload.path}")
    private String uploadPath;

    @Value("${img.path}")
    private String imgPath;
    private final BookService bookService;

    @Autowired
    public FileController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/file/save/{id}")
    public String saveFile(@PathVariable("id") int id, @RequestParam("image") MultipartFile multipartFile) throws IOException {
        if (!multipartFile.isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdir();

            String filename = id + multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf('.'));

            FileEntity file = bookService.getFileByName(filename);
            if(file != null){
                file.setName(filename);
                bookService.saveFile(file);
            } else bookService.saveFile(new FileEntity(filename,bookService.getBook(id)));

            Path path = Paths.get(uploadPath).resolve(filename);
            Files.copy(multipartFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            log.info("Saved file with name: " + filename);
        }

        return "redirect:/admin/edit/" + id;
    }

    @GetMapping("/image/{id}")
    public @ResponseBody void getImage(@PathVariable("id") int id, HttpServletResponse response) throws IOException, NullPointerException {
        FileEntity image = bookService.getImageFileById(id);
        response.setContentType("image/jpeg");
        if(image == null) {
            log.info("Image didn't found, set default image");
            getImage("not_found", response);
        }
        else {
            InputStream in = new FileInputStream(uploadPath + image.getName());
            IOUtils.copy(in, response.getOutputStream());
            in.close();
        }
    }

    @GetMapping("/logo/{name}")
    public @ResponseBody void getImage(@PathVariable("name") String name, HttpServletResponse response) throws IOException, NullPointerException {
        response.setContentType("image/jpeg");
        InputStream in = new FileInputStream(imgPath + name + ".jpg");
        IOUtils.copy(in, response.getOutputStream());
        in.close();
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> download(@PathVariable("id") int id) throws IOException {
        File downloadFile = new File(uploadPath + bookService.getTxtFileById(id).getName());
        if (!downloadFile.exists()) {
            return ResponseEntity.noContent()
                    .build();
        }
        InputStreamResource resource = new InputStreamResource(new FileInputStream(downloadFile));
        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + downloadFile.getName());
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");
        return ResponseEntity.ok()
                .headers(header)
                .contentLength(downloadFile.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }
}
