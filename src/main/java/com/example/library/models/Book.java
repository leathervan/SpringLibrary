package com.example.library.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Book implements BasicModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Size(max = 255)
    @NotNull
    @Column(name = "author", nullable = false)
    private String author;

    @Size(max = 1020)
    @NotNull
    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    private EGrade grade;

    @Column(name = "amount")
    private Integer amount;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<FileEntity> files = new ArrayList<>();

    @ManyToMany(mappedBy = "books")
    private List<User> users = new ArrayList<>();

    public Book(String name, String author, String description, Integer grade, Integer amount) {
        this.name = name;
        this.author = author;
        this.description = description;
        if(grade == null) this.grade = EGrade.DEFAULT;
        else this.grade = EGrade.values()[grade];
        this.amount = amount;
    }

    public void setFile(FileEntity file) {
        if(!files.contains(file)) files.add(file);
        file.setBook(this);
    }

    public void removeFile(FileEntity file) {
        files.remove(file);
        file.setBook(null);
    }

    public void addUser(User user) {
        users.add(user);
        user.getBooks().add(this);
    }

    public void removeUser(User user) {
        users.remove(user);
        user.getBooks().remove(this);
    }
}
