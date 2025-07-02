package io.github.Guimaraes131.libraryapi.controller.mappers;

import io.github.Guimaraes131.libraryapi.controller.dto.GetBookDTO;
import io.github.Guimaraes131.libraryapi.controller.dto.PostBookDTO;
import io.github.Guimaraes131.libraryapi.model.Book;
import io.github.Guimaraes131.libraryapi.repository.AuthorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class BookMapper {

    @Autowired
    AuthorRepository authorRepository;

    @Mapping(target = "author", expression = "java( authorRepository.findById(dto.authorId()).orElse(null) )")
    public abstract Book toEntity(PostBookDTO dto);

    public abstract GetBookDTO toDTO(Book book);
}
