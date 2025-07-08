package io.github.Guimaraes131.libraryapi.controller;

import io.github.Guimaraes131.libraryapi.controller.dto.AuthorDTO;
import io.github.Guimaraes131.libraryapi.controller.dto.ErrorResponse;
import io.github.Guimaraes131.libraryapi.controller.mappers.AuthorMapper;
import io.github.Guimaraes131.libraryapi.exception.DuplicateRecordException;
import io.github.Guimaraes131.libraryapi.exception.UnauthorizedOperationException;
import io.github.Guimaraes131.libraryapi.model.Author;
import io.github.Guimaraes131.libraryapi.service.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController implements GenericController {

    private final AuthorService service;
    private final AuthorMapper mapper;

    @PostMapping
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<?> create(@RequestBody @Valid AuthorDTO dto) {
        try {
            Author author = mapper.toEntity(dto);
            service.create(author);

            URI location = generateLocationHeader(author.getId());

            return ResponseEntity.created(location).build();

        } catch (DuplicateRecordException e) {
            ErrorResponse errorResponse = ErrorResponse.conflict("This record already exists.");

            return ResponseEntity.status(errorResponse.status()).body(errorResponse);
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('MANAGER', 'OPERATOR')")
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
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<?> destroy(@PathVariable String id) {
        try {
            UUID uuid = UUID.fromString(id);

            return service.get(uuid)
                    .map(author -> {
                        service.destroy(author);
                        return ResponseEntity.noContent().build();
                    }).orElseGet(() -> ResponseEntity.notFound().build());

        } catch (UnauthorizedOperationException e) {
            ErrorResponse error = ErrorResponse.badRequest(
                    "Failed to delete record: resource is being referenced by another entity.");

            return ResponseEntity.status(error.status()).body(error);
        }
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('MANAGER', 'OPERATOR')")
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
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<?> update(@RequestBody @Valid AuthorDTO dto, @PathVariable String id) {
        try {
            UUID uuid = UUID.fromString(id);

            return service.get(uuid)
                    .map(entity -> {
                        mapper.updateFromDTO(dto, entity);
                        service.update(entity);

                        return ResponseEntity.noContent().build();
                    }).orElseGet(() -> ResponseEntity.notFound().build());

        } catch (DuplicateRecordException e) {
            ErrorResponse errorResponse = ErrorResponse.conflict("This record already exists.");

            return ResponseEntity.status(errorResponse.status()).body(errorResponse);
        }
    }
}
