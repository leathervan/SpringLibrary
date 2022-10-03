package com.serhiiostapenko.OnlineLibrary.dto;


import com.serhiiostapenko.OnlineLibrary.entity.Book;
import com.serhiiostapenko.OnlineLibrary.entity.BookFile;
import com.serhiiostapenko.OnlineLibrary.entity.Genre;

import java.util.ArrayList;
import java.util.List;

public class BookDto {
    private Integer id;
    private String name;
    private String author;
    private String description;
    private Double rating;
    private List<Genre> genres = new ArrayList<>();
    private BookFile bookFile;

    public BookDto(Book book, BookFile bookFile) {
        this.id = book.getId();
        this.name = book.getName();
        this.author = book.getAuthor();
        this.description = book.getDescription();
        this.rating = book.getRating();
        this.bookFile = bookFile;
    }

    public Book getBook() {
        if (this.getRating() == null) this.setRating(0D);
        return new Book(this.name, this.author, this.description, this.rating);
    }

    public void updateBook(Book book) {
        if (!this.getName().isEmpty()) book.setName(this.getName());
        if (!this.getAuthor().isEmpty()) book.setAuthor(this.getAuthor());
        if (!this.getDescription().isEmpty()) book.setDescription(this.getDescription());
        if (this.getRating() != null) book.setRating(this.getRating());
    }

    public BookDto() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public BookFile getBookFile() {
        return bookFile;
    }

    public void setBookFile(BookFile bookFile) {
        this.bookFile = bookFile;
    }

    @Override
    public String toString() {
        return "BookDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", rating=" + rating +
                ", genres=" + genres +
                ", file=" + bookFile +
                '}';
    }
}
