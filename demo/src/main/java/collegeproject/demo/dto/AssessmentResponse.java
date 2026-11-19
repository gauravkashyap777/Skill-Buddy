package collegeproject.demo.dto;

import collegeproject.demo.entity.Assessment;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AssessmentResponse {
    private Long id;
    private String skill;
    private Integer score;
    private Integer total;
    private Integer percentage;
    private Boolean passed;
    private LocalDateTime completedAt;

    public static AssessmentResponse from(Assessment a) {
        if (a == null) return null;
        AssessmentResponse r = new AssessmentResponse();
        r.setId(a.getId());
        r.setSkill(a.getSkill());
        r.setScore(a.getScore());
        r.setTotal(a.getTotal());
        r.setPercentage(a.getPercentage());
        r.setPassed(a.getPassed());
        r.setCompletedAt(a.getCompletedAt());
        return r;
    }
}