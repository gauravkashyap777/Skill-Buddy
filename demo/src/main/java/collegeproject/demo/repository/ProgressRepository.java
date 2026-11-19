package collegeproject.demo.repository;

import collegeproject.demo.entity.Progress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProgressRepository extends JpaRepository<Progress, Long> {
    Optional<Progress> findByUserIdAndSkill(Long userId, String skill);
    List<Progress> findByUserId(Long userId);
}
