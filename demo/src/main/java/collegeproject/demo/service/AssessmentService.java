package collegeproject.demo.service;

import collegeproject.demo.dto.AssessmentRequest;
import collegeproject.demo.entity.Assessment;
import collegeproject.demo.entity.User;
import collegeproject.demo.repository.AssessmentRepository;
import collegeproject.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class AssessmentService {

    private final AssessmentRepository assessmentRepo;
    private final UserRepository userRepo;

    public AssessmentService(AssessmentRepository assessmentRepo, UserRepository userRepo) {
        this.assessmentRepo = assessmentRepo;
        this.userRepo = userRepo;
    }

    public Assessment submit(Long userId, AssessmentRequest req) {
        User user = userRepo.findById(userId).orElseThrow();

        Assessment assessment = Assessment.builder()
                .user(user)
                .skill(req.getSkill())
                .score(req.getScore())
                .total(req.getTotal())
                .percentage(req.getPercentage())
                .passed(req.getPassed())
                .completedAt(LocalDateTime.now())
                .build();

        return assessmentRepo.save(assessment);
    }

    public List<Assessment> getAll(Long userId) {
        return assessmentRepo.findByUserIdOrderByCompletedAtDesc(userId);
    }

    public Assessment getLatest(Long userId) {
        return assessmentRepo.findFirstByUserIdOrderByCompletedAtDesc(userId).orElse(null);
    }

    public Map<String, Assessment> getBySkill(Long userId) {
        List<Assessment> all = assessmentRepo.findByUserIdOrderByCompletedAtDesc(userId);

        Map<String, Assessment> map = new LinkedHashMap<>();
        for (Assessment a : all) {
            map.putIfAbsent(a.getSkill(), a);
        }
        return map;
    }
}