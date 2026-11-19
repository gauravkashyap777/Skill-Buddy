package collegeproject.demo.dto;

import collegeproject.demo.entity.Progress;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class ProgressResponse {
    private Long id;
    private String skill;
    private Set<Integer> completedLessons;
    private Integer percent;
    private Boolean courseCompleted;
    private LocalDateTime updatedAt;

    public static ProgressResponse from(Progress p) {
        if (p == null) return null;
        ProgressResponse r = new ProgressResponse();
        r.setId(p.getId());
        r.setSkill(p.getSkill());
        r.setCompletedLessons(p.getCompletedLessons());
        r.setPercent(p.getPercent());
        r.setCourseCompleted(p.getCourseCompleted());
        r.setUpdatedAt(p.getUpdatedAt());
        return r;
    }
}