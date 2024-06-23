package com.frx.literalura.model;

import com.google.gson.annotations.Expose;
import jakarta.persistence.*;
import com.google.gson.*;


@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Author author;
    private Integer year;
    private String language;

    public Book(RBook rBook) {
        this.title = rBook.title();
        this.author = new Author(rBook.Author());
        this.year = rBook.year();
        this.language = rBook.language();
    }

    public Book() {

    }

    public Long getId() {
        return id;
    }

    public String getLanguage() {
        return language;
    }

    public Author getAuthor() {
        return author;
    }

    public int getYear() {
        return year;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author=" + author +
                ", year=" + year +
                ", language='" + language + '\'' +
                '}';
    }

}
