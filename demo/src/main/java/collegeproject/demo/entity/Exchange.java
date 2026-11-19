package collegeproject.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "exchanges")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Exchange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @Column(nullable = false, length = 20)
    private String type;

    @Column(nullable = false, length = 60)
    private String skill;

    @Column(length = 1000)
    private String description;

    @Column(name = "partner_name")
    private String partnerName;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}