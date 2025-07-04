package io.github.Guimaraes131.libraryapi.service;

import io.github.Guimaraes131.libraryapi.model.Book;
import io.github.Guimaraes131.libraryapi.model.Genre;
import io.github.Guimaraes131.libraryapi.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static io.github.Guimaraes131.libraryapi.repository.specs.BookSpecs.*;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository repository;

    public Book create(Book book) {
        return repository.save(book);
    }

    public Optional<Book> get(UUID id) {
        return repository.findById(id);
    }

    public void destroy(Book book) {
        repository.delete(book);
    }

    public List<Book> index(String isbn, String title,
                            String authorName, Genre genre,
                            Integer publicationYear) {

        // deprecated
        Specification<Book> specs = Specification.where((root, query, cb) -> cb.conjunction());

        if (isbn != null) {
            specs = specs.and(isbnEqual(isbn));
        }

        if (title != null) {
            specs = specs.and(titleLike(title));
        }

        if (genre != null) {
            specs = specs.and(genreEqual(genre));
        }

        if (publicationYear != null) {
            specs = specs.and(publicationYearEqual(publicationYear));
        }

        if (authorName != null) {
            specs = specs.and(authorNameLike(authorName));
        }

        return repository.findAll(specs);
    }

    public void update(Book book) {
        repository.save(book);
    }
}
