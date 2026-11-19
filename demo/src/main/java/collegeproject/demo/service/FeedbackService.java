package collegeproject.demo.service;

import collegeproject.demo.dto.FeedbackRequest;
import collegeproject.demo.entity.Feedback;
import collegeproject.demo.entity.User;
import collegeproject.demo.repository.FeedbackRepository;
import collegeproject.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepo;
    private final UserRepository userRepo;

    public FeedbackService(FeedbackRepository feedbackRepo, UserRepository userRepo) {
        this.feedbackRepo = feedbackRepo;
        this.userRepo = userRepo;
    }

    public Feedback submit(Long userId, FeedbackRequest req) {
        User user = userRepo.findById(userId).orElseThrow();

        Feedback feedback = Feedback.builder()
                .user(user)
                .skill(req.getSkill())
                .mentorName(req.getMentorName() == null ? "Skill Buddy Mentor" : req.getMentorName())
                .rating(req.getRating())
                .review(req.getReview())
                .score(req.getScore() == null ? 0 : req.getScore())
                .submittedAt(LocalDateTime.now())
                .build();

        return feedbackRepo.save(feedback);
    }

    public List<Feedback> getAll(Long userId) {
        return feedbackRepo.findByUserIdOrderBySubmittedAtDesc(userId);
    }
}