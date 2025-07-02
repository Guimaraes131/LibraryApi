package io.github.Guimaraes131.libraryapi.controller.mappers;

import io.github.Guimaraes131.libraryapi.controller.dto.AuthorDTO;
import io.github.Guimaraes131.libraryapi.model.Author;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    Author toEntity(AuthorDTO dto);

    AuthorDTO toDTO(Author author);
}
