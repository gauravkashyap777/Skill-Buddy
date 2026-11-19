package collegeproject.demo.dto;

import lombok.Data;

import java.util.Set;

@Data
public class ProgressRequest {
    private String skill;
    private Set<Integer> completedLessons;
    private Integer totalLessons;
}