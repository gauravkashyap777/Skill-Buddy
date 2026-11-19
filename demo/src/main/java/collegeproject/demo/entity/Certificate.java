package collegeproject.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "certificates", uniqueConstraints = @UniqueConstraint(columnNames = "certificate_id"))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Certificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @Column(name = "certificate_id", nullable = false, unique = true)
    private String certificateId;

    @Column(name = "student_name", nullable = false)
    private String studentName;

    @Column(nullable = false, length = 60)
    private String skill;

    @Column(name = "course_title")
    private String courseTitle;

    @Column(name = "mentor_name")
    private String mentorName;

    private Integer score;

    @Column(name = "completed_at")
    private LocalDateTime completedAt = LocalDateTime.now();
}