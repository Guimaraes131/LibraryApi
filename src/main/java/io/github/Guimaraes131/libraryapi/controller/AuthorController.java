package io.github.Guimaraes131.libraryapi.controller;

import io.github.Guimaraes131.libraryapi.controller.mappers.AuthorMapper;
import io.github.Guimaraes131.libraryapi.exception.DuplicateRecordException;
import io.github.Guimaraes131.libraryapi.controller.dto.AuthorDTO;
import io.github.Guimaraes131.libraryapi.controller.dto.ErrorResponse;
import io.github.Guimaraes131.libraryapi.exception.UnauthorizedOperationException;
import io.github.Guimaraes131.libraryapi.model.Author;
import io.github.Guimaraes131.libraryapi.service.AuthorService;
import io.github.Guimaraes131.libraryapi.validator.AuthorValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController implements GenericController {

    private final AuthorService service;
    private final AuthorValidator validator;
    private final AuthorMapper mapper;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid AuthorDTO dto) {
        try {
            Author author = mapper.toEntity(dto);
            validator.validate(author);
            service.create(author);

            URI location = generateLocationHeader(author.getId());

            return ResponseEntity.created(location).build();

        } catch (DuplicateRecordException e) {
            ErrorResponse errorResponse = ErrorResponse.conflict("This record already exists.");

            return ResponseEntity.status(errorResponse.status()).body(errorResponse);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> get(@PathVariable String id) {
        UUID authorId = UUID.fromString(id);

        return service
                .get(authorId)
                .map(author -> {
                    AuthorDTO dto = mapper.toDTO(author);
                    return ResponseEntity.ok(dto);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> destroy(@PathVariable String id) {
        try {
            UUID authorId = UUID.fromString(id);

            Optional<Author> optionalAuthor = service.get(authorId);

            if (optionalAuthor.isPresent()) {
                validator.validate(optionalAuthor.get());
                service.destroy(optionalAuthor.get());
            }

            return ResponseEntity.noContent().build();

        } catch (UnauthorizedOperationException e) {
            ErrorResponse error = ErrorResponse.badRequest(
                    "Failed to delete record: resource is being referenced by another entity.");

            return ResponseEntity.status(error.status()).body(error);
        }
    }

    @GetMapping
    public ResponseEntity<List<AuthorDTO>> index(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "nationality", required = false) String nationality) {

        List<Author> authors = service.index(name, nationality);

        List<AuthorDTO> dtos = authors.stream()
                .map(mapper::toDTO)
                .toList();

        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody @Valid AuthorDTO dto, @PathVariable String id) {
        try {
            UUID authorId = UUID.fromString(id);

            Optional<Author> authorOptional = service.get(authorId);

            if (authorOptional.isPresent()) {
                Author entity = authorOptional.get();

                entity.setName(dto.name());
                entity.setNationality(dto.nationality());
                entity.setDateOfBirth(dto.dateOfBirth());

                validator.validate(entity);
                service.update(entity);
            }

            return ResponseEntity.noContent().build();

        } catch (DuplicateRecordException e) {
            ErrorResponse errorResponse = ErrorResponse.conflict("This record already exists.");

            return ResponseEntity.status(errorResponse.status()).body(errorResponse);
        }
    }
}
