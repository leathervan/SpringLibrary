package com.example.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    private Integer id;
    private String name;
    private String author;
    private String description;
    private Integer grade;
    private Integer amount;
    private MultipartFile image;
    private MultipartFile file;

    public BookDto(Integer id, String name, String author, String description, Integer grade, Integer amount) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.description = description;
        this.grade = grade;
        this.amount = amount;
    }
}