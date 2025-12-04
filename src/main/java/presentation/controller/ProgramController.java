package presentation.controller;

import application.programs.CreateProgramUseCase;
import application.programs.UpdateProgramUseCase;
import application.programs.DeleteProgramUseCase;
import application.programs.ChangeProgramStateUseCase;
import application.programs.SearchProgramsUseCase;
import application.programs.ViewProgramUseCase;
import application.programs.AddProgrammerUseCase;
import application.programs.AddStaffUseCase;
import domain.entity.value.ProgramId;
import domain.entity.value.UserId;
import domain.enums.ProgramState;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import presentation.dto.requests.CreateProgramRequest;
import presentation.dto.requests.UpdateProgramRequest;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/programs")
public class ProgramController {

    private final CreateProgramUseCase createProgram;
    private final UpdateProgramUseCase updateProgram;
    private final DeleteProgramUseCase deleteProgram;
    private final ViewProgramUseCase viewProgram;
    private final SearchProgramsUseCase searchPrograms;
    private final AddProgrammerUseCase addProgrammer;
    private final AddStaffUseCase addStaff;
    private final ChangeProgramStateUseCase changeState;

    public ProgramController(CreateProgramUseCase createProgram,
                             UpdateProgramUseCase updateProgram,
                             DeleteProgramUseCase deleteProgram,
                             ViewProgramUseCase viewProgram,
                             SearchProgramsUseCase searchPrograms,
                             AddProgrammerUseCase addProgrammer,
                             AddStaffUseCase addStaff,
                             ChangeProgramStateUseCase changeState) {
        this.createProgram = createProgram;
        this.updateProgram = updateProgram;
        this.deleteProgram = deleteProgram;
        this.viewProgram = viewProgram;
        this.searchPrograms = searchPrograms;
        this.addProgrammer = addProgrammer;
        this.addStaff = addStaff;
        this.changeState = changeState;
    }

    // =======================
    // 🆕 CREATE PROGRAM
    // =======================
    @PostMapping
    public ResponseEntity<Void> create(@RequestParam("creatorId") Long creatorId,
                                       @RequestBody CreateProgramRequest request) {

        // προσαρμόζεις στη signature του use case σου
        createProgram.create(
                new UserId(creatorId),
                request.name(),
                request.description(),
                request.startDate(),
                request.endDate()
        );

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // =======================
    // ✏ UPDATE PROGRAM
    // =======================
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id,
                                       @RequestParam Long actorUserId,   // ✅ ποιος κάνει το update
                                       @RequestBody UpdateProgramRequest request
    ) {

        updateProgram.update(
                new UserId(actorUserId),
                new ProgramId(id),
                request.name(),
                request.description(),
                request.startDate(),
                request.endDate()
        );

        return ResponseEntity.ok().build();
    }

    // =======================
    // 🗑 DELETE PROGRAM
    // =======================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long userId,
                                        @PathVariable Long id) {
        deleteProgram.delete(new UserId(userId),new ProgramId(id));
        return ResponseEntity.noContent().build();
    }

    // =======================
    // 👁 VIEW PROGRAM
    // =======================
    @GetMapping("/{id}")
    public ResponseEntity<?> view(@PathVariable Long id) {
        var program = viewProgram.view(new ProgramId(id));
        // εδώ ιδανικά κάνεις mapping σε ProgramResponse DTO
        return ResponseEntity.ok(program);
    }

    // =======================
    // 🔍 SEARCH PROGRAMS
    // =======================
    @GetMapping
    public ResponseEntity<?> search(@RequestParam(required = false) String name,
                                    @RequestParam(required = false)ProgramState programState,
                                    @RequestParam(required = false) LocalDate from,
                                    @RequestParam(required = false)  LocalDate to,
                                    @RequestParam(required = false)int offset,
                                    @RequestParam(required = false) int limit) {
        var result = searchPrograms.search(name, programState, from , to , offset, limit);
        // mapping σε λίστα από ProgramResponse αν θέλεις
        return ResponseEntity.ok(result);
    }


    @PostMapping("/{id}/programmers/{userId}")
    public ResponseEntity<Void> addProgrammer(@PathVariable Long ownreId,
                                                @PathVariable Long id,
                                              @PathVariable Long userId) {
        addProgrammer.addProgrammer(new UserId(ownreId),new ProgramId(id), new UserId(userId));
        return ResponseEntity.ok().build();
    }


    @PostMapping("/{id}/staff/{userId}")
    public ResponseEntity<Void> addStaff(@PathVariable Long programmerId,
                                         @PathVariable Long id,
                                         @PathVariable Long userId) {
        addStaff.addStaff(new UserId(programmerId), new ProgramId(id), new UserId(userId));
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/state")
    public ResponseEntity<Void> changeState(@PathVariable Long id,
                                            @RequestParam ProgramState newState,
                                            @RequestParam Long actorUserId) {


        changeState.changeState(
                new UserId(actorUserId),
                new ProgramId(id),
                newState
        );

        return ResponseEntity.ok().build();
    }
}
