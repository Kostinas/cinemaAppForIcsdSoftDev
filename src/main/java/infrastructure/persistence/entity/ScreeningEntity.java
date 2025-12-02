package infrastructure.persistence.entity;


import domain.enums.ScreeningState;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(
        name = "Screenings",
        indexes = {
                @Index(name = "idScreeningsProgram", columnList = "programId"),
                @Index(name = "idScreeningsSubmitter", columnList = "submitterId"),
                @Index(name = "idScreeningsStaff", columnList = "staffMemberId")

        }
)
public class ScreeningEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "programId", nullable = false)
    private Long programId;

    @Column(name = "submitterId", nullable = false)
    private Long submitterId;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(length = 100)
    private String genre;

    @Column(length = 4000)
    private String description;

    @Column(length = 100)
    private String room;

    private LocalDate scheduledTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ScreeningState screeningState;

    @Column(name = "staffMemberId")
    private Long staffMemberId;

    private LocalDate submittedTime;
    private LocalDate reviewedTime;


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getProgramId() { return programId; }
    public void setProgramId(Long programId) { this.programId = programId; }

    public Long getSubmitterId() { return submitterId; }
    public void setSubmitterId(Long submitterId) { this.submitterId = submitterId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getRoom() { return room; }
    public void setRoom(String room) { this.room = room; }

    public LocalDate getScheduledTime() { return scheduledTime; }
    public void setScheduledTime(LocalDate scheduledTime) { this.scheduledTime = scheduledTime; }

    public ScreeningState getState() { return screeningState; }
    public void setState(ScreeningState screeningState) { this.screeningState = screeningState; }

    public Long getStaffMemberId() { return staffMemberId; }
    public void setStaffMemberId(Long staffMemberId) { this.staffMemberId = staffMemberId; }

    public LocalDate getSubmittedTime() { return submittedTime; }
    public void setSubmittedTime(LocalDate submittedTime) { this.submittedTime = submittedTime; }

    public LocalDate getReviewedTime() { return reviewedTime; }
    public void setReviewedTime(LocalDate reviewedTime) { this.reviewedTime = reviewedTime; }
}








