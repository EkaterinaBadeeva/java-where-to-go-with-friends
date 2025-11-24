package com.my.pet.project.where_to_go_with_friends.compilation.service;

import com.my.pet.project.where_to_go_with_friends.compilation.dto.CompilationDto;
import com.my.pet.project.where_to_go_with_friends.compilation.dto.NewCompilationDto;
import com.my.pet.project.where_to_go_with_friends.compilation.dto.UpdateCompilationRequest;

import java.util.List;

public interface CompilationService {

    CompilationDto create(NewCompilationDto compilationDto);

    CompilationDto updateCompilation(UpdateCompilationRequest compilationDto, Long compId);

    void deleteCompilationById(Long compId);

    List<CompilationDto> getCompilations(Boolean pinned, Integer from, Integer size);

    CompilationDto getCompilationById(Long compId);
}
