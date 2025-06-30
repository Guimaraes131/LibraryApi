package io.github.Guimaraes131.libraryapi.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "livro")
public class Book {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "isbn")
    private String isbn;

    @Column(name = "titulo")
    private String title;

    @Column(name = "data_publicacao")
    private LocalDate publicationDate;

    @Column(name = "genero")
    @Enumerated(EnumType.STRING)
    private Genre genre;

    @Column(name = "preco")
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "id_autor", nullable = false)
    private Author author;
}
