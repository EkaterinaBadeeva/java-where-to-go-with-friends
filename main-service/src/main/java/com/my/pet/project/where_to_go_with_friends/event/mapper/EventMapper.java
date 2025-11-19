package com.my.pet.project.where_to_go_with_friends.event.mapper;

import com.my.pet.project.where_to_go_with_friends.category.mapper.CategoryMapper;
import com.my.pet.project.where_to_go_with_friends.event.dto.EventFullDto;
import com.my.pet.project.where_to_go_with_friends.event.dto.EventShortDto;
import com.my.pet.project.where_to_go_with_friends.event.dto.LocationDto;
import com.my.pet.project.where_to_go_with_friends.event.dto.NewEventDto;
import com.my.pet.project.where_to_go_with_friends.event.model.Event;
import com.my.pet.project.where_to_go_with_friends.user.mapper.UserMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EventMapper {

    public static EventFullDto mapToEventFullDto(Event event) {
        return EventFullDto.builder()
                .annotation(event.getAnnotation())
                .category(CategoryMapper.mapToCategoryDto(event.getCategory()))
                .confirmedRequests(event.getConfirmedRequests())
                .createdOn(event.getCreatedOn())
                .description(event.getDescription())
                .eventDate(event.getEventDate())
                .id(event.getId())
                .initiator(UserMapper.mapToUserShortDto(event.getInitiator()))
                .location(LocationDto.builder()
                        .lat(event.getLocation().getLat())
                        .lon(event.getLocation().getLon())
                        .build())
                .paid(event.getPaid())
                .participantLimit(event.getParticipantLimit())
                .publishedOn(event.getPublishedOn())
                .requestModeration(event.getRequestModeration())
                .state(event.getState())
                .title(event.getTitle())
                .views(event.getViews())
                .build();
    }

    public static Event mapToEvent(EventFullDto eventDto) {
        Event event = new Event();
        event.setAnnotation(eventDto.getAnnotation());
        event.setCategory(CategoryMapper.mapToCategory(eventDto.getCategory()));
        event.setConfirmedRequests(event.getConfirmedRequests());
        event.setCreatedOn(eventDto.getCreatedOn());
        event.setDescription(eventDto.getDescription());
        event.setEventDate(eventDto.getEventDate());
        event.setLocation(event.getLocation());
        event.setPaid(eventDto.getPaid());
        event.setParticipantLimit(eventDto.getParticipantLimit());
        event.setPublishedOn(event.getPublishedOn());
        event.setRequestModeration(eventDto.getRequestModeration());
        event.setState(eventDto.getState());
        event.setTitle(eventDto.getTitle());
        event.setViews(eventDto.getViews());
        return event;
    }

    public static Event mapToEvent(NewEventDto eventDto) {
        Event event = new Event();
        event.setAnnotation(eventDto.getAnnotation());
        event.setDescription(eventDto.getDescription());
        event.setEventDate(eventDto.getEventDate());
        event.setPaid(eventDto.getPaid());
        event.setParticipantLimit(eventDto.getParticipantLimit());
        event.setRequestModeration(eventDto.getRequestModeration());
        event.setTitle(eventDto.getTitle());
        return event;
    }

    public static EventShortDto mapToEventShortDto(Event event) {
        return EventShortDto.builder()
                .annotation(event.getAnnotation())
                .category(CategoryMapper.mapToCategoryDto(event.getCategory()))
                .confirmedRequests(event.getConfirmedRequests())
                .eventDate(event.getEventDate())
                .id(event.getId())
                .initiator(UserMapper.mapToUserShortDto(event.getInitiator()))
                .paid(event.getPaid())
                .title(event.getTitle())
                .views(event.getViews())
                .build();
    }
}
