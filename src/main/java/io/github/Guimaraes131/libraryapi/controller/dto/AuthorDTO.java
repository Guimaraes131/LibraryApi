package io.github.Guimaraes131.libraryapi.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record AuthorDTO(
        UUID id,

        @NotBlank(message = "field name cannot be null or blank")
        String name,

        @NotNull(message = "field dateOfBirth cannot be null (YYYY-MM-DD format)")
        LocalDate dateOfBirth,

        @NotNull(message = "field nationality cannot be null")
        String nationality
) {
}
