package application.screenings;

import domain.Exceptions.AuthorizationException;
import domain.Exceptions.NotFoundException;
import domain.Exceptions.ValidationException;
import domain.entity.Screening;
import domain.entity.value.ScreeningId;
import domain.entity.value.UserId;
import domain.port.ScreeningRepository;

public final class FinalSubmitScreeningUseCase {

    private final ScreeningRepository screeningRepository;

    public FinalSubmitScreeningUseCase(ScreeningRepository screeningRepository){
        this.screeningRepository = screeningRepository;
    }

    public void finalSubmit(UserId submitterId , ScreeningId screeningId){

        Screening screening = screeningRepository.findById(screeningId)
                .orElseThrow(()-> new NotFoundException("Screening", "Screening not found"));

        if(!screening.staffMemberId().equals(submitterId)){
            throw new AuthorizationException("Only the submitter can final-submit this screening");
        }

        if(screening.state() != screening.state().ACCEPTED){
            throw new ValidationException("state", "Only ACCEPTED screenings can be final-submitted");
        }

        screeningRepository.save(screening);
    }

}
