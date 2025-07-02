package io.github.Guimaraes131.libraryapi.controller.dto;

import io.github.Guimaraes131.libraryapi.model.Genre;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;
import org.hibernate.validator.constraints.ISBN;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record PostBookDTO(
        @ISBN(message = "ISBN already exists")
        @NotBlank(message = "ISBN field cannot be blank or null")
        String isbn,

        @NotBlank(message = "Title field cannot be blank or null")
        String title,

        @NotNull(message = "publicationDate field cannot be null")
        @PastOrPresent(message = "publicationDate field needs to be in the past or present")
        LocalDate publicationDate,
        Genre genre,
        BigDecimal price,

        @NotNull(message = "authorId field cannot be null")
        UUID authorId
) {
}
