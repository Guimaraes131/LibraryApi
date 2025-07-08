package io.github.Guimaraes131.libraryapi.controller.mappers;

import io.github.Guimaraes131.libraryapi.controller.dto.PostUserDTO;
import io.github.Guimaraes131.libraryapi.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(PostUserDTO dto);
}
