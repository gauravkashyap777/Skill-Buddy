package collegeproject.demo.dto;

import collegeproject.demo.entity.Certificate;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CertificateResponse {
    private Long id;
    private String certificateId;
    private String studentName;
    private String skill;
    private String courseTitle;
    private String mentorName;
    private Integer score;
    private LocalDateTime completedAt;

    public static CertificateResponse from(Certificate c) {
        if (c == null) return null;
        CertificateResponse r = new CertificateResponse();
        r.setId(c.getId());
        r.setCertificateId(c.getCertificateId());
        r.setStudentName(c.getStudentName());
        r.setSkill(c.getSkill());
        r.setCourseTitle(c.getCourseTitle());
        r.setMentorName(c.getMentorName());
        r.setScore(c.getScore());
        r.setCompletedAt(c.getCompletedAt());
        return r;
    }
}