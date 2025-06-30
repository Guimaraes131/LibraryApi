package io.github.Guimaraes131.libraryapi.repository;

import io.github.Guimaraes131.libraryapi.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AuthorRepository extends JpaRepository<Author, UUID> {

    List<Author> findByNameAndNationality(String name, String nationality);
    List<Author> findByName(String name);
    List<Author> findByNationality(String nationality);

    Optional<Author> findByNameAndNationalityAndDateOfBirth(String name, String nationality, LocalDate dateOfBirth);
}
