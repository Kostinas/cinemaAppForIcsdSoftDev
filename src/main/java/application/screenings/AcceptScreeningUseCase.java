package application.screenings;

import domain.Exceptions.AuthorizationException;
import domain.Exceptions.NotFoundException;
import domain.entity.Screening;
import domain.entity.value.ProgramId;
import domain.entity.value.ScreeningId;
import domain.entity.value.UserId;
import domain.port.ProgramRepository;
import domain.port.ScreeningRepository;

import java.time.LocalDate;

public final class AcceptScreeningUseCase {
    private final ScreeningRepository  screeningRepository;
    private  final ProgramRepository programRepository;

    public AcceptScreeningUseCase(ScreeningRepository screeningRepository , ProgramRepository programRepository){
        this.screeningRepository = screeningRepository;
        this.programRepository = programRepository;
    }

    public void acceptAndSchedule(
            UserId programmerId,
            ScreeningId screeningId,
            LocalDate date,
            String room
    ){
        Screening screening = screeningRepository.findById(screeningId)
                .orElseThrow( ()-> new NotFoundException("Screening", "Screening not found"));

        ProgramId programId =screening.programId();

        if(!programRepository.isProgrammer(programId , programmerId)){
            throw new AuthorizationException("Only PROGRAMMER can accept & schedule screenings");
        }

        screening.schedule(date , room);
        screeningRepository.save(screening);

    }

}
