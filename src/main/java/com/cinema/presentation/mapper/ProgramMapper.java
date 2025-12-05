package com.cinema.presentation.mapper;

import com.cinema.domain.entity.Program;
import com.cinema.presentation.dto.responses.ProgramResponse;

public class ProgramMapper {

    private ProgramMapper() {
    }

    public static ProgramResponse toResponse(Program program) {
        if (program == null) return null;

        return new ProgramResponse(
                program.id() != null ? program.id().value() : null,
                program.name(),
                program.description(),
                program.startDate(),
                program.endDate(),
                program.state() != null ? program.state().name() : null
        );
    }

}
