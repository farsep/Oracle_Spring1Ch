package com.frx.literalura.repository;

import com.frx.literalura.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IAuthorRepo extends JpaRepository<Author, Long> {

    List<Author> findBybirthYear(int year);
}
