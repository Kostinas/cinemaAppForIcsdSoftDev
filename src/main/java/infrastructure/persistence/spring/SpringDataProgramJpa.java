package infrastructure.persistence.spring;

import domain.enums.ProgramState;
import infrastructure.persistence.entity.ProgramEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface SpringDataProgramJpa extends JpaRepository<ProgramEntity, Long> {

    List<ProgramEntity> findByNameContainingIgnoreCase(String name);

    List<ProgramEntity> findByState(ProgramState state);

    List<ProgramEntity> findByStartDateBetween(LocalDate from, LocalDate to);

}