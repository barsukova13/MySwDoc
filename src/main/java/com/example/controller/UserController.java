package com.example.controller;

import com.example.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User API", description = "Управление пользователями")
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    @Operation(summary = "Получить пользователя по ID")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO userDto = userService.getUserById(id);

        // Добавляем HATEOAS ссылки
        userDto.add(linkTo(methodOn(UserController.class).getUserById(id)).withSelfRel());
        userDto.add(linkTo(UserController.class).slash(id).withRel("update"));
        userDto.add(linkTo(UserController.class).slash(id).withRel("delete"));
        userDto.add(linkTo(UserController.class).withRel("all-users"));

        return ResponseEntity.ok(userDto);
    }

    @GetMapping
    @Operation(summary = "Получить всех пользователей")
    public ResponseEntity<CollectionModel<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();

        // Добавляем HATEOAS ссылки для коллекции
        CollectionModel<UserDTO> model = CollectionModel.of(users);
        model.add(linkTo(methodOn(UserController.class).getAllUsers()).withSelfRel());
        model.add(linkTo(UserController.class).withRel("create-user"));

        return ResponseEntity.ok(model);
    }

    @PostMapping
    @Operation(summary = "Создать нового пользователя")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDto) {
        UserDTO createdUser = userService.createUser(userDto);

        // Добавляем ссылки для нового ресурса
        createdUser.add(linkTo(methodOn(UserController.class).getUserById(createdUser.getId())).withSelfRel());

        return ResponseEntity
                .created(linkTo(methodOn(UserController.class).getUserById(createdUser.getId())).toUri())
                .body(createdUser);
    }

}
