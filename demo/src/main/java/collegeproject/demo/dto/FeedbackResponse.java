package collegeproject.demo.dto;

import collegeproject.demo.entity.Feedback;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FeedbackResponse {

    private Long id;
    private String skill;
    private String mentorName;
    private Integer rating;
    private String review;
    private Integer score;
    private LocalDateTime submittedAt;

    public static FeedbackResponse from(Feedback f) {
        if (f == null) return null;
        FeedbackResponse r = new FeedbackResponse();
        r.setId(f.getId());
        r.setSkill(f.getSkill());
        r.setMentorName(f.getMentorName());
        r.setRating(f.getRating());
        r.setReview(f.getReview());
        r.setScore(f.getScore());
        r.setSubmittedAt(f.getSubmittedAt());
        return r;
    }
}