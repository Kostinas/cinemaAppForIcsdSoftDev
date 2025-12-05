package application.programs;

import domain.Exceptions.AuthorizationException;
import domain.Exceptions.NotFoundException;
import domain.entity.Program;
import domain.entity.value.ProgramId;
import domain.entity.value.UserId;
import domain.enums.ProgramState;
import domain.port.ProgramRepository;
import domain.service.ProgramStateMachine;

public final class ChangeProgramStateUseCase {
    private final ProgramRepository programRepository;
    private final ProgramStateMachine stateMachine;

    public ChangeProgramStateUseCase(ProgramRepository programRepository , ProgramStateMachine programStateMachine){
        this.programRepository = programRepository;
        this.stateMachine = programStateMachine;
    }

    public void changeState(UserId userId,
                            ProgramId programId,
                            ProgramState newState) {

        Program program = programRepository.findById(programId)
                .orElseThrow(() -> new NotFoundException("Program", "Program not found"));

        if (!programRepository.isProgrammer(programId, userId))
            throw new AuthorizationException("Only programmers can change program state");

        ProgramState next = stateMachine.transition(program.state(), newState);
        program.changeState(next);
        programRepository.save(program);
    }
}


