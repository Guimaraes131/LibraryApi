package io.github.Guimaraes131.libraryapi.controller;

import io.github.Guimaraes131.libraryapi.controller.dto.ErrorResponse;
import io.github.Guimaraes131.libraryapi.controller.dto.GetBookDTO;
import io.github.Guimaraes131.libraryapi.controller.dto.PostBookDTO;
import io.github.Guimaraes131.libraryapi.controller.mappers.BookMapper;
import io.github.Guimaraes131.libraryapi.exception.DuplicateRecordException;
import io.github.Guimaraes131.libraryapi.model.Book;
import io.github.Guimaraes131.libraryapi.model.Genre;
import io.github.Guimaraes131.libraryapi.service.BookService;
import io.github.Guimaraes131.libraryapi.validator.BookValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController implements GenericController {

    private final BookService service;
    private final BookMapper mapper;
    private final BookValidator validator;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid PostBookDTO dto) {
        try {
            Book entity = mapper.toEntity(dto);
            validator.validate(entity);
            service.create(entity);

            URI location = generateLocationHeader(entity.getId());

            return ResponseEntity.created(location).build();
        } catch (DuplicateRecordException e) {
            ErrorResponse errorResponse = ErrorResponse.conflict("Duplicated ISBN.");

            return ResponseEntity.status(errorResponse.status()).body(errorResponse);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable String id) {
        UUID uuid = UUID.fromString(id);

        return service
                .get(uuid)
                .map(book -> {
                    GetBookDTO dto = mapper.toDTO(book);
                    return ResponseEntity.ok(dto);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> destroy(@PathVariable String id) {
        UUID uuid = UUID.fromString(id);

        return service.get(uuid)
                .map(book -> {
                    service.destroy(book);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<GetBookDTO>> index(
            @RequestParam(value = "isbn", required = false) String isbn,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "author-name", required = false) String authorName,
            @RequestParam(value = "genre", required = false) Genre genre,
            @RequestParam(value = "publication-year", required = false) Integer publicationYear
    ) {

        List<Book> books = service.index(isbn, title, authorName, genre, publicationYear);

        List<GetBookDTO> dtos = books.stream()
                .map(mapper::toDTO)
                .toList();

        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody @Valid PostBookDTO dto, @PathVariable String id) {
        try {
            UUID uuid = UUID.fromString(id);

            return service.get(uuid)
                    .map(entity -> {
                        mapper.updateFromDTO(dto, entity);
                        service.update(entity);

                        return ResponseEntity.noContent().build();
                    }).orElseGet(() -> ResponseEntity.notFound().build());

        } catch (DuplicateRecordException e) {
            ErrorResponse errorResponse = ErrorResponse.conflict("Duplicated ISBN.");

            return ResponseEntity.status(errorResponse.status()).body(errorResponse);
        }
    }
}
