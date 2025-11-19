package com.my.pet.project.where_to_go_with_friends.compilation.mapper;

import com.my.pet.project.where_to_go_with_friends.compilation.dto.CompilationDto;
import com.my.pet.project.where_to_go_with_friends.compilation.dto.NewCompilationDto;
import com.my.pet.project.where_to_go_with_friends.compilation.dto.UpdateCompilationRequest;
import com.my.pet.project.where_to_go_with_friends.compilation.model.Compilation;
import com.my.pet.project.where_to_go_with_friends.event.mapper.EventMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CompilationMapper {

    public static CompilationDto mapToCompilationDto(Compilation compilation) {
        return CompilationDto.builder()
                .events(compilation.getEvents().stream().map(EventMapper::mapToEventShortDto).toList())
                .id(compilation.getId())
                .pinned(compilation.getPinned())
                .title(compilation.getTitle())
                .build();
    }

    public static Compilation mapToCompilation(NewCompilationDto compilationDto) {
        Compilation compilation = new Compilation();
        compilation.setPinned(compilationDto.getPinned());
        compilation.setTitle(compilationDto.getTitle());
        return compilation;
    }

    public static Compilation mapToCompilation(UpdateCompilationRequest compilationDto) {
        Compilation compilation = new Compilation();
        compilation.setPinned(compilationDto.getPinned());
        compilation.setTitle(compilationDto.getTitle());
        return compilation;
    }
}
