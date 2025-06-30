package io.github.Guimaraes131.libraryapi.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "autor")
public class Author {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "nome")
    private String name;

    @Column(name = "data_nascimento")
    private LocalDate bornDate;

    @Column(name = "nacionalidade")
    private String nationality;

    @OneToMany(fetch = FetchType.LAZY)
    @Transient
    private List<Book> books;
}
