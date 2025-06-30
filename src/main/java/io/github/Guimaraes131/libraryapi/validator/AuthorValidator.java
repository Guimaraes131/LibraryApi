package io.github.Guimaraes131.libraryapi.validator;

import io.github.Guimaraes131.libraryapi.Exception.DuplicateRecordException;
import io.github.Guimaraes131.libraryapi.model.Author;
import io.github.Guimaraes131.libraryapi.repository.AuthorRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthorValidator {

    private final AuthorRepository repository;

    public AuthorValidator(AuthorRepository repository) {
        this.repository = repository;
    }

    public void validate(Author author) {
        if (authorExists(author)) {
            throw new DuplicateRecordException("Author already exists");
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
}
