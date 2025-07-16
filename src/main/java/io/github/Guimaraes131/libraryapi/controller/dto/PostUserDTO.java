package io.github.Guimaraes131.libraryapi.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record PostUserDTO(
        @NotBlank(message = "Login field cannot be null or blank")
        String login,

        @NotBlank(message = "Password field cannot be null or blank")
        String password,

        @Email(message = "Invalid email")
        @NotBlank(message = "Email field cannot be null or blank")
        String email,

        List<String> roles) {
}
