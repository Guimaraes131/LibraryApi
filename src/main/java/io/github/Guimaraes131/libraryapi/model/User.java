package io.github.Guimaraes131.libraryapi.model;

import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_user")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String login;
    private String password;
    private String email;

    @Type(ListArrayType.class)
    @Column(name = "roles", columnDefinition = "VARCHAR[]")
    private List<String> roles;
}
