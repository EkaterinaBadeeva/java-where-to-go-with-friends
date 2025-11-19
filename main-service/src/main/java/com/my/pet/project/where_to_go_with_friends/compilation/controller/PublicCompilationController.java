package com.my.pet.project.where_to_go_with_friends.compilation.controller;

import com.my.pet.project.where_to_go_with_friends.compilation.dto.CompilationDto;
import com.my.pet.project.where_to_go_with_friends.compilation.service.CompilationService;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/compilations")
@RequiredArgsConstructor
public class PublicCompilationController {
    private final CompilationService compilationService;

    //GET  /compilations?pinned={pinned}&from={from}&size={size}
    // получить подбороки событий
    // from - количество элементов, которые нужно пропустить для формирования текущего набора,
    // size - количество категорий в наборе
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CompilationDto> getCompilations(@RequestParam(defaultValue = "false") Boolean pinned,
                                                @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                                @RequestParam(defaultValue = "10") @Positive Integer size) {
        return compilationService.getCompilations(pinned, from, size);
    }

    //GET  /compilations?pinned={pinned}&from={from}&size={size}
    // получить подбороку события по его id
    @GetMapping("/{compId}")
    @ResponseStatus(HttpStatus.OK)
    public CompilationDto getCompilationById(@PathVariable Long compId) {
        return compilationService.getCompilationById(compId);
    }
}
