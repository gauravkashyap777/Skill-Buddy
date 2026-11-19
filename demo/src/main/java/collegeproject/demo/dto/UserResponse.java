package collegeproject.demo.dto;

import collegeproject.demo.entity.User;
import lombok.Data;

import java.util.Set;

@Data
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private String role;
    private Set<String> learningSkills;
    private Set<String> teachingSkills;
    private String currentLearningSkill;
    private Integer points;
    private Integer badges;

    public static UserResponse from(User u) {
        if (u == null) return null;
        UserResponse r = new UserResponse();
        r.setId(u.getId());
        r.setUsername(u.getUsername());
        r.setEmail(u.getEmail());
        r.setRole(u.getRole());
        r.setLearningSkills(u.getLearningSkills());
        r.setTeachingSkills(u.getTeachingSkills());
        r.setCurrentLearningSkill(u.getCurrentLearningSkill());
        r.setPoints(u.getPoints());
        r.setBadges(u.getBadges());
        return r;
    }
}