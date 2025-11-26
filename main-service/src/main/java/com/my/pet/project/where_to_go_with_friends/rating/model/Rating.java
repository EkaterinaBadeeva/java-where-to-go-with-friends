package com.my.pet.project.where_to_go_with_friends.rating.model;

import com.my.pet.project.where_to_go_with_friends.event.model.Event;
import com.my.pet.project.where_to_go_with_friends.user.model.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "ratings")
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Rating {
    //id — уникальный идентификатор оценки
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    //created — дата и время(в формате "yyyy-MM-dd HH:mm:ss"), когда была поставлена оценка (like/dislike)
    @Column(name = "created", nullable = false)
    LocalDateTime created;

    //event — событие, которое оценил пользователь
    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    Event event;

    //user — пользователь, который поставил оценку
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    //state — состояние оценки (like/dislike)
    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    RatingState state;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rating rating = (Rating) o;
        return Objects.equals(event, rating.event) && Objects.equals(user, rating.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(event, user);
    }
}



