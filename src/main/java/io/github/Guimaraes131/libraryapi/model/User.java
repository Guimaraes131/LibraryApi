package io.github.Guimaraes131.libraryapi.model;

import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Type;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "tb_user")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String login;
    private String password;

    @Type(ListArrayType.class)
    @Column(name = "roles", columnDefinition = "VARCHAR[]")
    private List<String> roles;
}
