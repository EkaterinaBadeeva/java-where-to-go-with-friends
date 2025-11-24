package com.my.pet.project.where_to_go_with_friends.service;

import com.my.pet.project.where_to_go_with_friends.HitDto;
import com.my.pet.project.where_to_go_with_friends.ViewStatsDto;

import java.time.LocalDateTime;

import java.util.Collection;
import java.util.List;

public interface HitService {

    HitDto saveHit(HitDto hitDto);

    Collection<ViewStatsDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique);
}
