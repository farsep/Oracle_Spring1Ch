package com.frx.literalura.repository;

import com.frx.literalura.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBookRepo extends JpaRepository<Book, Long> {

    Iterable<Object> findByLanguage(String language);
}
