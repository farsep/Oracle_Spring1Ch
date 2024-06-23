package com.frx.literalura.model;

public record RBook(
        String title,
        RAuthor Author,
        Integer year,
        String language
) {
}
