package collegeproject.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "progress", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "skill"})
})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Progress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @Column(nullable = false, length = 60)
    private String skill;

    @Builder.Default
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "progress_completed_lessons", joinColumns = @JoinColumn(name = "progress_id"))
    @Column(name = "lesson_index")
    private Set<Integer> completedLessons = new HashSet<>();

    @Builder.Default
    private Integer percent = 0;

    @Builder.Default
    @Column(name = "course_completed")
    private Boolean courseCompleted = false;

    @Builder.Default
    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();
}