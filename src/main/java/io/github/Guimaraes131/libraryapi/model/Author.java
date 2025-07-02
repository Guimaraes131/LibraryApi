package io.github.Guimaraes131.libraryapi.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "autor")
@Data
@EntityListeners(AuditingEntityListener.class)
public class Author {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "nome")
    private String name;

    @Column(name = "data_nascimento")
    private LocalDate dateOfBirth;

    @Column(name = "nacionalidade")
    private String nationality;

    @Transient
    private List<Book> books;

    @CreatedDate
    @Column(name = "data_cadastro")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "data_atualizacao")
    private LocalDateTime updatedAt;

    @Column(name = "id_usuario")
    private UUID userID;
}
