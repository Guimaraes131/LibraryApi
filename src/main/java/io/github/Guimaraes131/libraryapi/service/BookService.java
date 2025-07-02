package io.github.Guimaraes131.libraryapi.service;

import io.github.Guimaraes131.libraryapi.model.Author;
import io.github.Guimaraes131.libraryapi.model.Book;
import io.github.Guimaraes131.libraryapi.model.Genre;
import io.github.Guimaraes131.libraryapi.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
                            LocalDate publicationDate) {

        Book book = new Book();
        Author author = new Author();

        author.setName(authorName);

        book.setIsbn(isbn);
        book.setTitle(title);
        book.setAuthor(author);
        book.setPublicationDate(publicationDate);
        book.setGenre(genre);

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<Book> example = Example.of(book, matcher);

        return repository.findAll(example);
    }
}
