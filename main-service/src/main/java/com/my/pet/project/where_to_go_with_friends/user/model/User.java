package com.my.pet.project.where_to_go_with_friends.user.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.Objects;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    //id — уникальный идентификатор пользователя
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    //name — имя или логин пользователя
    @Column(name = "name", nullable = false)
    String name;

    //email — адрес электронной почты
    @Column(name = "email", nullable = false, unique = true)
    String email;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }
}



