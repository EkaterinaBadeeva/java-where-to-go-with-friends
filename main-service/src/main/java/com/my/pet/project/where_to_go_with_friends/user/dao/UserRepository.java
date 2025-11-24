package com.my.pet.project.where_to_go_with_friends.user.dao;

import com.my.pet.project.where_to_go_with_friends.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    Page<User> findUsersByIdIn(List<Long> ids, Pageable pageable);
}
