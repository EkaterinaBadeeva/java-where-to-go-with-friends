package com.my.pet.project.where_to_go_with_friends.user.service;

import com.my.pet.project.where_to_go_with_friends.user.dto.UserDto;
import com.my.pet.project.where_to_go_with_friends.user.model.User;

import java.util.List;

public interface UserService {
    List<UserDto> getUsers(List<Long> ids, Integer from, Integer size);

    UserDto create(UserDto userDto);

    void deleteUserById(Long id);

    User findUserById(Long userId);
}
