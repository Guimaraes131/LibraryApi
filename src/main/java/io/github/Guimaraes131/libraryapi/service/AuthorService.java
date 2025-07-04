package io.github.Guimaraes131.libraryapi.service;

import io.github.Guimaraes131.libraryapi.model.Author;
import io.github.Guimaraes131.libraryapi.repository.AuthorRepository;
import io.github.Guimaraes131.libraryapi.validator.AuthorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository repository;
    private final AuthorValidator validator;

    public void create(Author author) {
        validator.validate(author);
        repository.save(author);
    }

    public Optional<Author> get(UUID id) {
        return repository.findById(id);
    }

    public void destroy(Author author) {
        repository.delete(author);
    }

    public List<Author> index(String name, String nationality) {
        Author author = new Author();
        author.setName(name);
        author.setNationality(nationality);

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<Author> example = Example.of(author, matcher);

        return repository.findAll(example);
    }

    public void update(Author author) {
        if (author.getId() == null) {
            throw new IllegalArgumentException("It's not possible to update an Author withou ID");
        }

        validator.validate(author);
        repository.save(author);
    }
}
