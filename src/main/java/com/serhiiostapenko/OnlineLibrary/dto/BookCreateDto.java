package com.serhiiostapenko.OnlineLibrary.dto;


import com.serhiiostapenko.OnlineLibrary.entity.Genre;

import java.util.ArrayList;
import java.util.List;

public class BookCreateDto {
    private String name;
    private String author;
    private String description;
    private Double rating;
    private List<Genre> genres = new ArrayList<>();
    private String pic;
    private String path;

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
    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "BookCreateDto{" +
                "name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", description='" + description + '\'' +
                ", rating=" + rating +
                ", genres=" + genres +
                ", pic='" + pic + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
