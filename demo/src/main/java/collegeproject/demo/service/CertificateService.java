package collegeproject.demo.service;

import collegeproject.demo.dto.CertificateRequest;
import collegeproject.demo.entity.Assessment;
import collegeproject.demo.entity.Certificate;
import collegeproject.demo.entity.Feedback;
import collegeproject.demo.entity.User;
import collegeproject.demo.repository.AssessmentRepository;
import collegeproject.demo.repository.CertificateRepository;
import collegeproject.demo.repository.FeedbackRepository;
import collegeproject.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class CertificateService {

    private final CertificateRepository certificateRepo;
    private final AssessmentRepository assessmentRepo;
    private final FeedbackRepository feedbackRepo;
    private final UserRepository userRepo;

    private static final Map<String, String> COURSE_TITLES = Map.ofEntries(
            Map.entry("Java", "Java Programming"),
            Map.entry("Python", "Python Programming"),
            Map.entry("JavaScript", "JavaScript Programming"),
            Map.entry("SQL", "SQL & Database"),
            Map.entry("Data Structures", "Data Structures & Algorithms"),
            Map.entry("Machine Learning", "Machine Learning"),
            Map.entry("Data Science", "Data Science"),
            Map.entry("Spring Boot", "Spring Boot Development"),
            Map.entry("React", "React"),
            Map.entry("Web Development", "Web Development"),
            Map.entry("Android", "Android Development")
    );

    public CertificateService(CertificateRepository certificateRepo,
                              AssessmentRepository assessmentRepo,
                              FeedbackRepository feedbackRepo,
                              UserRepository userRepo) {
        this.certificateRepo = certificateRepo;
        this.assessmentRepo = assessmentRepo;
        this.feedbackRepo = feedbackRepo;
        this.userRepo = userRepo;
    }

    public Certificate generate(Long userId, CertificateRequest req) {
        Assessment passedAssessment = assessmentRepo
                .findFirstByUserIdAndSkillAndPassedTrueOrderByCompletedAtDesc(userId, req.getSkill())
                .orElseThrow(() -> new RuntimeException("Assessment not passed for this skill"));

        Feedback feedback = feedbackRepo.findFirstByUserIdAndSkill(userId, req.getSkill())
                .orElseThrow(() -> new RuntimeException("Feedback not submitted"));

        Certificate existing = certificateRepo.findByUserIdAndSkill(userId, req.getSkill()).orElse(null);
        if (existing != null) return existing;

        User user = userRepo.findById(userId).orElseThrow();

        String certId = "SB-" + java.time.Year.now().getValue() + "-" +
                (int) (Math.random() * 900000 + 100000);

        Certificate cert = Certificate.builder()
                .user(user)
                .certificateId(certId)
                .studentName(user.getUsername())
                .skill(req.getSkill())
                .courseTitle(COURSE_TITLES.getOrDefault(req.getSkill(), req.getSkill() + " Course"))
                .mentorName(feedback.getMentorName())
                .score(passedAssessment.getPercentage())
                .completedAt(LocalDateTime.now())
                .build();

        certificateRepo.save(cert);

        user.setPoints(user.getPoints() + passedAssessment.getPercentage());
        user.setBadges(user.getBadges() + 1);
        userRepo.save(user);

        return cert;
    }

    public List<Certificate> getAll(Long userId) {
        return certificateRepo.findByUserIdOrderByCompletedAtDesc(userId);
    }
}