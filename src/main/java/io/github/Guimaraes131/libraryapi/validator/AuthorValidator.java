package io.github.Guimaraes131.libraryapi.validator;

import io.github.Guimaraes131.libraryapi.exception.DuplicateRecordException;
import io.github.Guimaraes131.libraryapi.exception.UnauthorizedOperationException;
import io.github.Guimaraes131.libraryapi.model.Author;
import io.github.Guimaraes131.libraryapi.model.Book;
import io.github.Guimaraes131.libraryapi.repository.AuthorRepository;
import io.github.Guimaraes131.libraryapi.repository.BookRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class AuthorValidator {

    private final AuthorRepository repository;
    private final BookRepository bookRepository;

    public AuthorValidator(AuthorRepository repository, BookRepository bookRepository) {
        this.repository = repository;
        this.bookRepository = bookRepository;
    }

    public void validate(Author author) {
        if (authorExists(author)) {
            throw new DuplicateRecordException("Author already exists");
        }

        if (authorHasBooks(author)) {
            throw new UnauthorizedOperationException("Cannot delete an author who has books.");
        }
    }

    public boolean authorExists(Author author) {
        Optional<Author> optionalAuthor = repository
                .findByNameAndNationalityAndDateOfBirth(
                        author.getName(),
                        author.getNationality(),
                        author.getDateOfBirth()
                );

        if (author.getId() == null && optionalAuthor.isPresent()) {
            return true;
        }

        assert author.getId() != null;
        return optionalAuthor.isPresent() && !author.getId().equals(optionalAuthor.get().getId());
    }

    public boolean authorHasBooks(Author author) {
        List<Book> books = bookRepository.findAllByAuthor(author);

        if (!books.isEmpty()) {
            return true;
        }

        return false;
    }
}
