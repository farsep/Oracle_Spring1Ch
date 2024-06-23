package com.frx.literalura.model;

import com.google.gson.annotations.Expose;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer birthYear;
    private Integer death;
    @Transient
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ArrayList<Book> books = new ArrayList<>();

    public Author(RAuthor rAuthor) {
        this.name = rAuthor.name();
        this.birthYear = rAuthor.birthyear();
        this.death = rAuthor.deathyear();
    }

    public Author() {

    }

    public void addBook(Book book) {
        this.books.add(book);
    }

    public String getName() {
        return name;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public int getDeath() {
        return death;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthYear=" + birthYear +
                ", death=" + death +
                '}';
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
