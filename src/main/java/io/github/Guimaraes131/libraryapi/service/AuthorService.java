package io.github.Guimaraes131.libraryapi.service;

import io.github.Guimaraes131.libraryapi.model.Author;
import io.github.Guimaraes131.libraryapi.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthorService {

    private final AuthorRepository repository;

    public AuthorService(AuthorRepository repository) {
        this.repository = repository;
    }

    public void create(Author author) {
        repository.save(author);
    }

    public Optional<Author> get(UUID id) {
        return repository.findById(id);
    }

    public void destroy(Author author) {
        repository.delete(author);
    }

    public List<Author> index(String name, String nationality) {
        if (name != null && nationality != null) {
            return repository.findByNameAndNationality(name, nationality);
        }

        if (name != null) {
            return repository.findByName(name);
        }

        if (nationality != null) {
            return repository.findByNationality(nationality);
        }

        return repository.findAll();
    }

    public void update(Author author) {
        repository.save(author);
    }
}
