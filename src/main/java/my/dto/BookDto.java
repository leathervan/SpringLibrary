package my.dto;

import my.entity.Book;
import my.entity.BookHasGenre;
import my.entity.File;
import my.entity.Genre;
import my.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
public class BookDto {
    private Integer id;
    private String name;
    private String author;
    private String description;
    private Double rating;
    private List<Genre> genres = new ArrayList<>();
    private File file;

    public BookDto(Book book, List<Genre> genres, File file) {
        this.id = book.getId();
        this.name = book.getName();
        this.author = book.getAuthor();
        this.description = book.getDescription();
        this.rating = book.getRating();
        for (BookHasGenre bookHasGenre : book.getBookHasGenresById()){
            if(genres.contains(bookHasGenre.getGenreByGenreId())) this.genres.add(bookHasGenre.getGenreByGenreId());
        }
        this.file = file;
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

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "BookDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", rating=" + rating +
                ", genres=" + genres +
                ", file=" + file.getPic() +
                '}';
    }
}
