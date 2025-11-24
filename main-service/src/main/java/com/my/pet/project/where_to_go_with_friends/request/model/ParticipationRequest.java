package com.my.pet.project.where_to_go_with_friends.request.model;

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
@Table(name = "requests")
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ParticipationRequest {
    //id — уникальный идентификатор заявки
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    //created — дата и время создания заявки (в формате "yyyy-MM-dd HH:mm:ss")
    @Column(name = "created", nullable = false)
    LocalDateTime created;

    //event — событие
    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    Event event;

    //requester — пользователь, отправивший заявку
    @ManyToOne
    @JoinColumn(name = "requester_id", nullable = false)
    User requester;

    //status — статус заявки
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    ParticipationRequestStatus status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParticipationRequest category = (ParticipationRequest) o;
        return Objects.equals(id, category.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}



