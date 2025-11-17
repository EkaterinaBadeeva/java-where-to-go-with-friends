package com.my.pet.project.service;

import com.my.pet.project.HitDto;
import com.my.pet.project.ViewStatsDto;
import com.my.pet.project.ViewStatsDtoProjection;
import com.my.pet.project.dao.HitRepository;
import com.my.pet.project.exceptions.ValidationException;
import com.my.pet.project.mapper.HitMapper;
import com.my.pet.project.model.Hit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HitServiceImpl implements HitService {
    private final HitRepository hitRepository;

    @Override
    @Transactional
    public HitDto saveHit(HitDto hitDto) {
        log.info("Добавление информации о том, что на uri конкретного сервиса был отправлен запрос пользователем.");
        Hit hit = HitMapper.mapToHit(hitDto);
        hit = hitRepository.save(hit);
        return HitMapper.mapToHitDto(hit);
    }

    @Override
    public Collection<ViewStatsDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        log.info("Получение статистики по посещениям.");
        if (start.isAfter(end)) {
            throw new ValidationException("Указана дата и время начала диапазона позже чем " +
                    "дата и время конца диапазона за который нужно выгрузить статистику");
        }
        Collection<ViewStatsDtoProjection> viewsStats = List.of();

        if (uris == null || uris.isEmpty()) {
            if (unique) {
                viewsStats = hitRepository.findAllHitsAndTimestampBetweenStartEndAndUniqueIp(start, end);
            } else {
                viewsStats = hitRepository.findAllHitsAndTimestampBetweenStartEnd(start, end);
            }
        } else {
            if (unique) {
                viewsStats = hitRepository.findAllHitsAndTimestampBetweenStartEndAndUrisInAndUniqueIp(start, end, uris);
            } else {
                viewsStats = hitRepository.findAllHitsAndTimestampBetweenStartEndAndUrisIn(start, end, uris);
            }
        }

        return viewsStats.stream().map(stat -> ViewStatsDto.builder()
                        .app(stat.getApp())
                        .uri(stat.getUri())
                        .hits(stat.getHits())
                        .build())
                        .toList();
    }
}