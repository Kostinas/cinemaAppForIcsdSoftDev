package infrastructure.persistence.spring;

import domain.enums.ScreeningState;
import infrastructure.persistence.entity.ScreeningEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.List;

public interface SpringDataScreeningJpa extends JpaRepository<ScreeningEntity, Long> {

    List<ScreeningEntity> findByProgramIdAndState(Long programId, ScreeningState state, Pageable pageable);

    List<ScreeningEntity> findBySubmitterId(Long submitterId, Pageable pageable);

    List<ScreeningEntity> findByStaffMemberId(Long staffId, Pageable pageable);

    long countByProgramIdAndState(Long programId, ScreeningState state);
}