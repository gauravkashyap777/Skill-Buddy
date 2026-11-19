package collegeproject.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "feedback")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @Column(nullable = false, length = 60)
    private String skill;

    @Column(name = "mentor_name")
    private String mentorName;

    private Integer rating;

    @Column(length = 2000)
    private String review;

    private Integer score;

    @Builder.Default
    @Column(name = "submitted_at")
    private LocalDateTime submittedAt = LocalDateTime.now();
}