package collegeproject.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "submissions")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "assignment_id", nullable = false)
    private Long assignmentId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(length = 3000)
    private String content;

    private Integer marks;

    @Builder.Default
    @Column(name = "submitted_at")
    private LocalDateTime submittedAt = LocalDateTime.now();
}