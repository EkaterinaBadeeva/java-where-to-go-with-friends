package com.my.pet.project.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Table(name = "hits")
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Hit {

    //id — уникальный идентификатор записи
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Integer id;

    //app — идентификатор сервиса для которого записывается информация
    @Column(name = "app", nullable = false)
    String app;

    //uri — URI для которого был осуществлен запрос
    @Column(name = "uri", nullable = false)
    String uri;

    //ip — IP-адрес пользователя, осуществившего запрос
    @Column(name = "ip", nullable = false)
    String ip;

    //timestamp — дата и время, когда был совершен запрос к эндпоинту (в формате "yyyy-MM-dd HH:mm:ss")
    @Column(name = "timestamp", nullable = false)
    LocalDateTime timestamp;
}