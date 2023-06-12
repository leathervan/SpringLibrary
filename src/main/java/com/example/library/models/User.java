package com.example.library.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements BasicModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @Size(max = 45)
    @NotNull
    @Column(name = "name", nullable = false, length = 45)
    private String name;

    @Size(max = 45)
    @NotNull
    @Column(name = "surname", nullable = false, length = 45)
    private String surname;

    @Size(max = 255)
    @NotNull
    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Enumerated(EnumType.STRING)
    private ERole role;
    @Enumerated(EnumType.STRING)
    private EGrade grade;

    @OneToOne(mappedBy = "user", orphanRemoval = true, fetch = FetchType.EAGER)
    private FileEntity avatar;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "user_book",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private List<Book> books = new ArrayList<>();

    public User(String email, String name, String surname, String password, Integer grade) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.password = password;
        if(grade == null) this.grade = EGrade.DEFAULT;
        else this.grade = EGrade.values()[grade];
    }

    public void setAvatar(FileEntity fileEntity){
        avatar = fileEntity;
        fileEntity.setUser(this);
    }

    public void removeBook(Book book) {
        this.books.remove(book);
        book.getUsers().remove(this);
    }

}