package com.my.pet.project.service;

import com.my.pet.project.HitDto;
import com.my.pet.project.ViewStatsDto;

import java.time.LocalDateTime;

import java.util.Collection;
import java.util.List;

public interface HitService {

    HitDto saveHit(HitDto hitDto);

    Collection<ViewStatsDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique);
}
