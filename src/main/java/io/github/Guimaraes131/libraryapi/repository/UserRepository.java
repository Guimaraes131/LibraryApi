package io.github.Guimaraes131.libraryapi.repository;

import io.github.Guimaraes131.libraryapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User findByLogin(String login);
}
