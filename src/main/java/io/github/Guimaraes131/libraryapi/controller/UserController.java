package io.github.Guimaraes131.libraryapi.controller;

import io.github.Guimaraes131.libraryapi.controller.dto.PostUserDTO;
import io.github.Guimaraes131.libraryapi.controller.mappers.UserMapper;
import io.github.Guimaraes131.libraryapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController implements GenericController {

    private final UserService service;
    private final UserMapper mapper;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody PostUserDTO dto) {
        var user = mapper.toEntity(dto);
        service.create(user);

        return ResponseEntity.created(generateLocationHeader(user.getId())).build();
    }
}
