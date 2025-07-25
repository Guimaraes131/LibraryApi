package io.github.Guimaraes131.libraryapi.service;

import io.github.Guimaraes131.libraryapi.model.Book;
import io.github.Guimaraes131.libraryapi.model.Genre;
import io.github.Guimaraes131.libraryapi.repository.BookRepository;
import io.github.Guimaraes131.libraryapi.security.SecurityService;
import io.github.Guimaraes131.libraryapi.validator.BookValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private final BookValidator validator;
    private final SecurityService securityService;

    public void create(Book book) {
        validator.validate(book);

        var user = securityService.getLoggedInUser();

        book.setUser(user);
        repository.save(book);
    }

    public Optional<Book> get(UUID id) {
        return repository.findById(id);
    }

    public void destroy(Book book) {
        repository.delete(book);
    }

    public Page<Book> index(String isbn, String title,
                            String authorName, Genre genre,
                            Integer publicationYear, Integer page,
                            Integer pageSize) {

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

        Pageable pr = PageRequest.of(page, pageSize);

        return repository.findAll(specs, pr);
    }

    public void update(Book book) {
        if (book.getId() == null) {
            throw new IllegalArgumentException("It's not possible to update a Book without ID.");
        }

        validator.validate(book);
        repository.save(book);
    }
}
