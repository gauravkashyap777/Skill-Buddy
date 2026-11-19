package collegeproject.demo.repository;

import collegeproject.demo.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByUserIdOrderBySubmittedAtDesc(Long userId);
    Optional<Feedback> findFirstByUserIdAndSkill(Long userId, String skill);
}
