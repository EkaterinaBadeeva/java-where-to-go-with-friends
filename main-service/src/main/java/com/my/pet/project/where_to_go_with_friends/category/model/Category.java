package com.my.pet.project.where_to_go_with_friends.category.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.Objects;

@Entity
@Table(name = "categories")
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Category {
    //id — уникальный идентификатор категории
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    //name — название категории
    @Column(name = "name", nullable = false, unique = true, length = 50)
    String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(name, category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}



