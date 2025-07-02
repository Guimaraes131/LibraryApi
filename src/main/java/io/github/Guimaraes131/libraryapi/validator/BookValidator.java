package io.github.Guimaraes131.libraryapi.validator;

import io.github.Guimaraes131.libraryapi.exception.BookPriceNotSetException;
import io.github.Guimaraes131.libraryapi.exception.DuplicateRecordException;
import io.github.Guimaraes131.libraryapi.model.Book;
import io.github.Guimaraes131.libraryapi.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BookValidator {

    private final BookRepository repository;

    public void validate(Book book) {
        if (filter(book)) {
            throw new DuplicateRecordException("ISBN is being used by another book.");
        }

        if (missPrice(book)) {
            throw new BookPriceNotSetException("Books published after 2020 need to have a price.");
        }
    }

    private boolean filter(Book book) {
        Optional<Book> optionalBook = repository.findByIsbn(book.getIsbn());

        if (book.getId() == null && optionalBook.isPresent()) {
            return true;
        }

        return optionalBook.isPresent() && !book.getId().equals(optionalBook.get().getId());
    }

    private boolean missPrice(Book book) {
        return book.getPublicationDate()
                .isAfter(LocalDate.of(2019, 12, 31)) && book.getPrice() == null;
    }
}
