package com.serhiiostapenko.OnlineLibrary.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.Set;
@Data
@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @NotNull(message = "Name shouldn`t be empty")
    @NotBlank(message = "Name shouldn`t be empty")
    @Column(name = "name", nullable = false)
    private String name;

    @Size(max = 45)
    @NotNull(message = "Author shouldn`t be empty")
    @NotBlank(message = "Author shouldn`t be empty")
    @Column(name = "author", nullable = false, length = 45)
    private String author;

    @Size(max = 2000)
    @NotNull(message = "Description shouldn`t be empty")
    @NotBlank(message = "Description shouldn`t be empty")
    @Column(name = "description", nullable = false, length = 2000)
    private String description;

    @NotNull(message = "Rating shouldn`t be empty")
    @Column(name = "rating", nullable = false)
    private Double rating;


    public Book(String name, String author, String description, Double rating) {
        this.name = name;
        this.author = author;
        this.description = description;
        this.rating = rating;
    }

    public Book() {}

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", description='" + description + '\'' +
                ", rating=" + rating +
                '}';
    }
}