package io.github.Guimaraes131.libraryapi.repository.specs;

import io.github.Guimaraes131.libraryapi.model.Book;
import io.github.Guimaraes131.libraryapi.model.Genre;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecs {

    public static Specification<Book> isbnEqual(String isbn) {
        return (root, query, cb) -> cb.equal(root.get("isbn"), isbn);
    }

    public static Specification<Book> titleLike(String title) {
        return (root, query, cb) -> cb.like( cb.upper(root.get("title")), "%" + title.toUpperCase() + "%");
    }

    public static Specification<Book> genreEqual(Genre genre) {
        return (root, query, cb) -> cb.equal(root.get("genre"), genre);
    }

    public static Specification<Book> publicationYearEqual(Integer publicationYear) {

        return (root, query, cb) -> cb.equal(cb.function(
                "to_char", String.class,
                root.get("publicationDate"), cb.literal("YYYY")),
                publicationYear.toString()
        );
    }

    public static Specification<Book> authorNameLike(String authorName) {
        return (root, query, cb) -> {
            Join<Object, Object> authorJoin = root.join("author", JoinType.LEFT);

            return cb.like(cb.upper(authorJoin.get("name")), "%" + authorName + "%");

//            return cb.like(cb.upper(root.get("author").get("name")), "%" + authorName + "%");
        };
    }
}
