package com.my.pet.project.where_to_go_with_friends.compilation.controller;

import com.my.pet.project.where_to_go_with_friends.compilation.dto.CompilationDto;
import com.my.pet.project.where_to_go_with_friends.compilation.dto.NewCompilationDto;
import com.my.pet.project.where_to_go_with_friends.compilation.dto.UpdateCompilationRequest;
import com.my.pet.project.where_to_go_with_friends.compilation.service.CompilationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/admin/compilations")
@RequiredArgsConstructor
public class AdminCompilationController {
    private final CompilationService compilationService;

    //POST /admin/compilations
    // добавить новую подборку (подборка может не содержать событий)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto create(@Valid @RequestBody NewCompilationDto compilationDto) {
        return compilationService.create(compilationDto);
    }

    //PATCH /admin/compilations/{compId}
    // обновить информацию о подборке
    @PatchMapping("/{compId}")
    @ResponseStatus(HttpStatus.OK)
    public CompilationDto updateCompilation(@Valid @RequestBody UpdateCompilationRequest compilationDto,
                                      @PathVariable Long compId) {
        return compilationService.updateCompilation(compilationDto, compId);
    }

    //DELETE /admin/compilations/{compId}
    // удалить подборку
    @DeleteMapping("/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompilation(@PathVariable Long compId) {
        compilationService.deleteCompilationById(compId);
    }
}
