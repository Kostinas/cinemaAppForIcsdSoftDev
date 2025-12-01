package application.screenings;

import domain.Exceptions.AuthorizationException;
import domain.Exceptions.NotFoundException;
import domain.entity.Screening;
import domain.entity.value.ScreeningId;
import domain.entity.value.UserId;
import domain.port.ProgramRepository;
import domain.port.ScreeningRepository;

public final class RejectScreeningUseCase {

    private final ScreeningRepository screeningRepository;
    private final ProgramRepository programRepository;

    public RejectScreeningUseCase(ScreeningRepository screeningRepository , ProgramRepository programRepository){
        this.screeningRepository = screeningRepository;
        this.programRepository = programRepository;
    }

    public void reject(UserId staffId , ScreeningId screeningId){

        Screening screening = screeningRepository.findById(screeningId)
                .orElseThrow(()-> new NotFoundException("Screening", "Screening not found"));

        if(!programRepository.isStaff(screening.programId(), staffId)){
            throw new AuthorizationException("Only STAFF of the program can reject");
        }

        if(screening.staffMemberId() == null || !screening.staffMemberId().equals(staffId)){
            throw new AuthorizationException("Only the assigned staff handler can reject this screening");
        }

        screening.reject();
        screeningRepository.save(screening);
    }
}
