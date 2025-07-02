package io.github.Guimaraes131.libraryapi.controller;

import io.github.Guimaraes131.libraryapi.controller.dto.GetBookDTO;
import io.github.Guimaraes131.libraryapi.controller.dto.PostBookDTO;
import io.github.Guimaraes131.libraryapi.controller.mappers.BookMapper;
import io.github.Guimaraes131.libraryapi.model.Book;
import io.github.Guimaraes131.libraryapi.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController implements GenericController {

    private final BookService service;
    private final BookMapper mapper;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody PostBookDTO dto) {
        Book entity = mapper.toEntity(dto);
        service.create(entity);

        URI location = generateLocationHeader(entity.getId());

        return ResponseEntity.created(location).build();
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
}
