package com.frx.literalura.principal;

import com.frx.literalura.model.Author;
import com.frx.literalura.model.Book;
import com.frx.literalura.model.RAuthor;
import com.frx.literalura.model.RBook;
import com.frx.literalura.repository.IAuthorRepo;
import com.frx.literalura.repository.IBookRepo;
import com.frx.literalura.service.Http;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class Executable {
    private Gson gson = new Gson();
    private final Scanner scanner = new Scanner(System.in);
    @Autowired
    private Http http;
    private final String url = "https://gutendex.com/books";
    @Autowired
    private IBookRepo bookRepo;
    @Autowired
    private IAuthorRepo authorRepo;

    public String toJson(Object object) {
        return gson.toJson(object);
    }

    public <T> T fromJson(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    public void execute() {
        System.out.println("Select your option \n");
        System.out.println("Search for a book and add it: 1");
        System.out.println("Show all books: 2");
        System.out.println("Show all authors: 3");
        System.out.println("Show all authors by year: 4");
        System.out.println("Show all books by language: 5");
        System.out.println("Exit: 6");

        int option = scanner.nextInt();
        scanner.nextLine();
        switch (option) {
            case 1:
                searchBook();
                break;
            case 2:
                showAllBooks();
                break;
            case 3:
                showAllAuthors();
                break;
            case 4:
                showAllAuthorsByYear();
                break;
            case 5:
                showAllBooksByLanguage();
                break;
            case 6:
                System.out.println("Exit");
                break;
            default:
                System.out.println("Invalid option");
                break;
        }

    }

    private void showAllBooksByLanguage() {
        System.out.println("Enter the language");
        String language = scanner.nextLine();
        bookRepo.findByLanguage(language).forEach(System.out::println);
    }

    private void showAllAuthorsByYear() {
        System.out.println("Enter the year");
        int year = scanner.nextInt();
        authorRepo.findBybirthYear(year).forEach(System.out::println);
    }

    private void showAllAuthors() {
        authorRepo.findAll().forEach(System.out::println);
    }

    private void showAllBooks() {
        bookRepo.findAll().forEach(System.out::println);
    }

    private void searchBook(){
        String formatTitle;
        AtomicReference<RAuthor> rauthor = new AtomicReference<>();
        AtomicReference<RBook> rbook = new AtomicReference<>();
        CompletableFuture<Void> future = null;
        while (true){
            try {
                System.out.println("Enter the book title");
                formatTitle = scanner.nextLine().replace(" ", "%20");
                System.out.println(url + "?search=" + formatTitle);
                future = http.getData(url + "?search=" + formatTitle)
                        .thenAccept(json -> {
                            System.out.println(json);
                            JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
                            if (jsonObject != null) {
                                rbook.set(gson.fromJson(jsonObject.get("results").getAsJsonArray().get(0).toString(), RBook.class));
                                Book book = new Book(rbook.get());
                                bookRepo.save(book);
                                System.out.println("Book added");
                                rauthor.set(gson.fromJson(jsonObject.get("results").getAsJsonArray().get(0).getAsJsonObject().get("authors").getAsJsonArray().get(0).toString(), RAuthor.class));
                                Author author = new Author(rauthor.get());
                                author.addBook(book);
                                authorRepo.save(author);
                                System.out.println("Author added");

                            } else {
                                System.out.println("Invalid JSON received");
                            }
                        });
                break;
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        }
        if (future != null) {
            CompletableFuture.allOf(future).join(); // wait for all futures to complete
        }
    }

}
