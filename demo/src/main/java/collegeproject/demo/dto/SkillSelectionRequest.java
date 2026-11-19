package collegeproject.demo.dto;

import lombok.Data;

import java.util.Set;

@Data
public class SkillSelectionRequest {
    private Set<String> learningSkills;
    private Set<String> teachingSkills;
}