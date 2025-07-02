package io.github.Guimaraes131.libraryapi.controller.dto;

import io.github.Guimaraes131.libraryapi.model.Genre;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record GetBookDTO(
        UUID id,
        String isbn,
        String title,
        LocalDate publicationDate,
        Genre genre,
        BigDecimal price,
        AuthorDTO author
) {
}
