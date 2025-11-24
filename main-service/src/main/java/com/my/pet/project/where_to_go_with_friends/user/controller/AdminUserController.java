package com.my.pet.project.where_to_go_with_friends.user.controller;

import com.my.pet.project.where_to_go_with_friends.user.dto.UserDto;
import com.my.pet.project.where_to_go_with_friends.user.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/admin/users")
@RequiredArgsConstructor
public class AdminUserController {
    private final UserService userService;

    //GET /admin/users?ids={ids}&from={from}&size={size}
    // получить информацию о пользователях
    // ids - id пользователей,
    // from - количество элементов, которые нужно пропустить для формирования текущего набора,
    // size - количество элементов в наборе
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> getUsers(@RequestParam(required = false) List<Long> ids,
                                  @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                  @RequestParam(defaultValue = "10") @Positive Integer size) {
        return userService.getUsers(ids, from, size);
    }

    //POST /admin/users
    // добавить пользователя
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@Valid @RequestBody UserDto userDto) {
        return userService.create(userDto);
    }

    //DELETE /users/{id}
    // удалить пользователя по id
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
    }
}
