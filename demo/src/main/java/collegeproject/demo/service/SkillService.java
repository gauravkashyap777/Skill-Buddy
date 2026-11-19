package collegeproject.demo.service;

import collegeproject.demo.dto.SkillSelectionRequest;
import collegeproject.demo.entity.User;
import collegeproject.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class SkillService {

    private final UserRepository userRepo;

    public SkillService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public User saveSkills(Long userId, SkillSelectionRequest req) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (req.getLearningSkills() != null && !req.getLearningSkills().isEmpty()) {
            user.setLearningSkills(req.getLearningSkills());
            user.setCurrentLearningSkill(req.getLearningSkills().iterator().next());
        }
        if (req.getTeachingSkills() != null) {
            user.setTeachingSkills(req.getTeachingSkills());
        }

        return userRepo.save(user);
    }

    public User getUser(Long userId) {
        return userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}