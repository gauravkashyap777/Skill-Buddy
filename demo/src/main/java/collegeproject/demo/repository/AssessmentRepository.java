package collegeproject.demo.repository;

import collegeproject.demo.entity.Assessment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AssessmentRepository extends JpaRepository<Assessment, Long> {

    List<Assessment> findByUserIdOrderByCompletedAtDesc(Long userId);

    Optional<Assessment> findFirstByUserIdAndSkillAndPassedTrueOrderByCompletedAtDesc(
            Long userId, String skill);

    Optional<Assessment> findFirstByUserIdOrderByCompletedAtDesc(Long userId);
}
