package com.my.pet.project.where_to_go_with_friends.compilation.model;

import com.my.pet.project.where_to_go_with_friends.event.model.Event;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "compilations")
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Compilation {
    //id — уникальный идентификатор подборки событий
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    //events — список событий входящих в подборку
    @ToString.Exclude
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "compilations_events",
            joinColumns = @JoinColumn(name = "compilation_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id"))
            List<Event> events;

    //pinned — закреплена ли подборка на главной странице сайта
    @Column(name = "pinned", nullable = false)
    Boolean pinned;

    //title — заголовок подборки
    @Column(name = "title", nullable = false)
    String title;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Compilation category = (Compilation) o;
        return Objects.equals(id, category.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
