package io.github.Guimaraes131.libraryapi.validator;

import io.github.Guimaraes131.libraryapi.exception.BookPriceNotSetException;
import io.github.Guimaraes131.libraryapi.exception.DuplicateRecordException;
import io.github.Guimaraes131.libraryapi.model.Book;
import io.github.Guimaraes131.libraryapi.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BookValidator {

    private static final int YEAR_PRICE_REQUIREMENT = 2020;

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

        if (book.getId() == null) {
            return optionalBook.isPresent();
        }

        return optionalBook.isPresent() && !book.getId().equals(optionalBook.get().getId());
    }

    private boolean missPrice(Book book) {
        return book.getPrice() == null && book.getPublicationDate().getYear() >= YEAR_PRICE_REQUIREMENT;
    }
}
