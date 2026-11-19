package collegeproject.demo.service;

import collegeproject.demo.dto.ProgressRequest;
import collegeproject.demo.entity.Progress;
import collegeproject.demo.entity.User;
import collegeproject.demo.repository.ProgressRepository;
import collegeproject.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProgressService {

    private final ProgressRepository progressRepo;
    private final UserRepository userRepo;

    public ProgressService(ProgressRepository progressRepo, UserRepository userRepo) {
        this.progressRepo = progressRepo;
        this.userRepo = userRepo;
    }

    public Progress update(Long userId, ProgressRequest req) {
        User user = userRepo.findById(userId).orElseThrow();

        Progress progress = progressRepo.findByUserIdAndSkill(userId, req.getSkill())
                .orElseGet(() -> Progress.builder()
                        .user(user)
                        .skill(req.getSkill())
                        .build());

        progress.setCompletedLessons(req.getCompletedLessons());

        int percent = (int) Math.min(100,
                Math.round((req.getCompletedLessons().size() * 100.0) / req.getTotalLessons()));
        progress.setPercent(percent);
        progress.setCourseCompleted(req.getCompletedLessons().size() == req.getTotalLessons());
        progress.setUpdatedAt(LocalDateTime.now());

        return progressRepo.save(progress);
    }

    public Progress get(Long userId, String skill) {
        return progressRepo.findByUserIdAndSkill(userId, skill).orElse(null);
    }

    public List<Progress> getAll(Long userId) {
        return progressRepo.findByUserId(userId);
    }
}