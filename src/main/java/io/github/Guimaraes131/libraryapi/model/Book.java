package io.github.Guimaraes131.libraryapi.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "livro")
@Data
@EntityListeners(AuditingEntityListener.class)
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

    @CreatedDate
    @Column(name = "data_cadastro")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "data_atualizacao")
    private LocalDateTime updatedAt;

    @Column(name = "id_usuario")
    private UUID userID;
}
