package com.my.pet.project;

import com.my.pet.project.exceptions.ClientException;
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

    public StatClient(@Value("${stats-server.url}") String uriBase, RestTemplate rest) {
        this.rest = rest;
        this.uriBase = uriBase;
    }

    public ResponseEntity<Object> saveHit(HitDto hitDto) {
        String uri = UriComponentsBuilder.fromHttpUrl(uriBase)
                .path("/hit")
                .toUriString();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> entity = new HttpEntity<>(hitDto, headers);

        ResponseEntity<Object> response = rest.exchange(uri, HttpMethod.POST, entity, Object.class);

        if (response.getStatusCode().is4xxClientError()) {
            throw new ClientException("Ошибка при  сохранение информации о том, " +
                    "что на uri конкретного сервиса был отправлен запрос пользователем");

        } else if (response.getStatusCode().is5xxServerError()) {
            throw new ClientException("Ошибка при  сохранение информации о том, " +
                    "что на uri конкретного сервиса был отправлен запрос пользователем");
        }

        return response;
    }

    public ResponseEntity<Object> getStats(LocalDateTime start, LocalDateTime end,
                                           List<String> uris, boolean unique) {
        String uri = UriComponentsBuilder.fromHttpUrl(uriBase)
                .path("/stats")
                .queryParam("start", start)
                .queryParam("end", end)
                .queryParam("uris", uris)
                .queryParam("unique", unique)
                .toUriString();
        ResponseEntity<Object> response = rest.exchange(uri, HttpMethod.GET,
                null, new ParameterizedTypeReference<>() {
                });

        if (response.getStatusCode().is4xxClientError()) {
            throw new ClientException("Ошибка при получении статистики по посещениям");

        } else if (response.getStatusCode().is5xxServerError()) {
            throw new ClientException("Ошибка при получении статистики по посещениям");
        }

        return response;
    }
}
