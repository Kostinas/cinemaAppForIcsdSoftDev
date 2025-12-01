package application.screenings;

import domain.entity.Screening;
import domain.entity.value.ProgramId;
import domain.entity.value.UserId;
import domain.enums.ScreeningState;
import domain.port.ScreeningRepository;

import java.util.List;

public class SearchScreeningsUseCase {

    private final ScreeningRepository screeningRepository;

    public SearchScreeningsUseCase(ScreeningRepository screeningRepository){ this.screeningRepository = screeningRepository; }

    public List<Screening> byProgram(
            ProgramId programId,
            ScreeningState state,
            int offset,
            int limit
            ){
        return screeningRepository.findByProgram(programId , state , offset, limit);
    }

    public List<Screening> bySubmitter(
            UserId submitterId,
            ScreeningState state,
            int offset,
            int limit
    ){
        return screeningRepository.findBySubmitter(submitterId , state , offset , limit);
    }

    public List<Screening> byAssignedStaff(
            UserId staffId,
            int offset,
            int limit
    ){
        return screeningRepository.findByStaffMember(staffId, offset, limit);
    }

}
