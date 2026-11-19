package collegeproject.demo.controller;

import collegeproject.demo.dto.SkillSelectionRequest;
import collegeproject.demo.dto.UserResponse;
import collegeproject.demo.security.UserPrincipal;
import collegeproject.demo.service.SkillService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/skills")
public class SkillController {

    private final SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @PostMapping("/select")
    public ResponseEntity<UserResponse> select(@AuthenticationPrincipal UserPrincipal principal,
                                               @RequestBody SkillSelectionRequest req) {
        return ResponseEntity.ok(
                UserResponse.from(skillService.saveSkills(principal.getId(), req))
        );
    }

    @GetMapping
    public ResponseEntity<UserResponse> get(@AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.ok(UserResponse.from(skillService.getUser(principal.getId())));
    }
}