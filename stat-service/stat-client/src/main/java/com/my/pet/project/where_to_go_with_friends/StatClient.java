package com.my.pet.project.where_to_go_with_friends;

import com.my.pet.project.where_to_go_with_friends.exceptions.ClientException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StatClient {
    protected final RestTemplate rest;
    private final String uriBase;

    public StatClient(@Value("${stat-server.url}") String uriBase, RestTemplate rest) {
        this.rest = rest;
        this.uriBase = uriBase;
    }

    public HitDto saveHit(HitDto hitDto) {
        String uri = UriComponentsBuilder.fromHttpUrl(uriBase)
                .path("/hit")
                .toUriString();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<HitDto> entity = new HttpEntity<>(hitDto, headers);

        ResponseEntity<HitDto> response = rest.exchange(uri, HttpMethod.POST, entity, HitDto.class);

        if (response.getStatusCode().is4xxClientError()) {
            throw new ClientException("Ошибка при  сохранение информации о том, " +
                    "что на uri конкретного сервиса был отправлен запрос пользователем");

        } else if (response.getStatusCode().is5xxServerError()) {
            throw new ClientException("Ошибка при  сохранение информации о том, " +
                    "что на uri конкретного сервиса был отправлен запрос пользователем");
        }

        return response.getBody();
    }

    public List<ViewStatsDto> getStats(LocalDateTime start, LocalDateTime end,
                                       List<String> uris, boolean unique) {
        String uri = UriComponentsBuilder.fromHttpUrl(uriBase)
                .path("/stats")
                .queryParam("start", start)
                .queryParam("end", end)
                .queryParam("uris", uris)
                .queryParam("unique", unique)
                .toUriString();
        ResponseEntity<List<ViewStatsDto>> response = rest.exchange(uri, HttpMethod.GET,
                null, new ParameterizedTypeReference<List<ViewStatsDto>>() {
                });

        if (response.getStatusCode().is4xxClientError()) {
            throw new ClientException("Ошибка при получении статистики по посещениям");

        } else if (response.getStatusCode().is5xxServerError()) {
            throw new ClientException("Ошибка при получении статистики по посещениям");
        }

        return response.getBody();
    }
}
