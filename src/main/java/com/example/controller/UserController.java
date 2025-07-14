package com.example.controller;

import com.example.dto.UserDTO;
import com.example.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "User Management", description = "API для управления пользователями")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "Создать нового пользователя",
            description = "Создаёт нового пользователя с указанными данными",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Пользователь успешно создан"),
                    @ApiResponse(responseCode = "400", description = "Некорректные входные данные")
            }
    )
    @PostMapping
    public UserDTO createUser(@RequestBody UserDTO userDTO) {
        return userService.createUser(userDTO);
    }

    @Operation(
            summary = "Получить пользователя по ID",
            description = "Возвращает пользователя с указанным идентификатором",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Пользователь найден"),
                    @ApiResponse(responseCode = "404", description = "Пользователь не найден")
            }
    )
    @GetMapping("/{id}")
    public UserDTO getUserById(
            @Parameter(description = "ID пользователя", example = "1")
            @PathVariable Long id) {
        return userService.getUserById(id);
    }

    @Operation(
            summary = "Получить всех пользователей",
            description = "Возвращает список всех пользователей в системе",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Список пользователей получен")
            }
    )
    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @Operation(
            summary = "Обновить данные пользователя",
            description = "Обновляет данные пользователя с указанным ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Данные пользователя обновлены"),
                    @ApiResponse(responseCode = "404", description = "Пользователь не найден")
            }
    )
    @PutMapping("/{id}")
    public UserDTO updateUser(
            @Parameter(description = "ID пользователя", example = "1")
            @PathVariable Long id,
            @RequestBody UserDTO userDTO) {
        return userService.updateUser(id, userDTO);
    }

    @Operation(
            summary = "Удалить пользователя",
            description = "Удаляет пользователя с указанным ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Пользователь удалён"),
                    @ApiResponse(responseCode = "404", description = "Пользователь не найден")
            }
    )
    @DeleteMapping("/{id}")
    public void deleteUser(
            @Parameter(description = "ID пользователя", example = "1")
            @PathVariable Long id) {
        userService.deleteUser(id);
    }
}


