package com.my.pet.project.where_to_go_with_friends.event.model;

import com.my.pet.project.where_to_go_with_friends.category.model.Category;
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
@Table(name = "events")
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Event {
    //id — уникальный идентификатор события
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    //annotation — краткое описание события
    @Column(name = "annotation", length = 2000)
    String annotation;

    //category — категория
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    Category category;

    //confirmedRequests — количество одобренных заявок на участие в данном событии
    @Transient
    Integer confirmedRequests = 0;

    //createdOn — дата и время создания события (в формате "yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_on")
    LocalDateTime createdOn;

    //description — полное описание события
    @Column(name = "description", length = 7000)
    String description;

    //eventDate — дата и время на которые намечено событие (в формате "yyyy-MM-dd HH:mm:ss")
    @Column(name = "event_date")
    LocalDateTime eventDate;

    //initiator — пользователь (краткая информация)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "initiator_id")
    User initiator;

    //location — широта и долгота места проведения события
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    Location location;

    //paid — нужно ли оплачивать участие
    @Column(name = "paid")
    Boolean paid;

    //participantLimit — ограничение на количество участников. Значение 0 - означает отсутствие ограничения
    @Column(name = "participant_limit")
    Integer participantLimit;

    //publishedOn — дата и время публикации события (в формате "yyyy-MM-dd HH:mm:ss")
    @Column(name = "published_on")
    LocalDateTime publishedOn;

    //requestModeration — нужна ли пре-модерация заявок на участие
    @Column(name = "request_moderation")
    Boolean requestModeration;

    //state — список состояний жизненного цикла события
    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    EventState state;

    //title — заголовок
    @Column(name = "title", length = 120)
    String title;

    //views — количество просмотров события
    @Transient
    Long views = 0L;

    //rating — рейтинг события
    @Transient
    int rating = 0;

    //ratingInitiator — рейтинг пользователя
    @Transient
    int ratingInitiator;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event category = (Event) o;
        return Objects.equals(id, category.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}