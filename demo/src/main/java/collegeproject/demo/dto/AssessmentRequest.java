package collegeproject.demo.dto;

import lombok.Data;

@Data
public class AssessmentRequest {
    private String skill;
    private Integer score;
    private Integer total;
    private Integer percentage;
    private Boolean passed;
}