package collegeproject.demo.dto;

import lombok.Data;

@Data
public class FeedbackRequest {
    private String skill;
    private String mentorName;
    private Integer rating;
    private String review;
    private Integer score;
}