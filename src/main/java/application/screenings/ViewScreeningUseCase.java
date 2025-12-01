package application.screenings;

import domain.Exceptions.NotFoundException;
import domain.entity.Screening;
import domain.entity.value.ScreeningId;
import domain.port.ScreeningRepository;

public final class ViewScreeningUseCase {

    private final ScreeningRepository screeningRepository;

    public ViewScreeningUseCase(ScreeningRepository screeningRepository){ this.screeningRepository = screeningRepository; }

    public Screening view(ScreeningId id){
        return screeningRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Screening", "Screening not found"));
    }

}
