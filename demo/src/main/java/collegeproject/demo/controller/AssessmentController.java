package collegeproject.demo.controller;

import collegeproject.demo.dto.AssessmentRequest;
import collegeproject.demo.dto.AssessmentResponse;
import collegeproject.demo.entity.Assessment;
import collegeproject.demo.security.UserPrincipal;
import collegeproject.demo.service.AssessmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/assessment")
public class AssessmentController {

    private final AssessmentService assessmentService;

    public AssessmentController(AssessmentService assessmentService) {
        this.assessmentService = assessmentService;
    }

    @PostMapping("/submit")
    public ResponseEntity<AssessmentResponse> submit(@AuthenticationPrincipal UserPrincipal principal,
                                                     @RequestBody AssessmentRequest req) {
        return ResponseEntity.ok(
                AssessmentResponse.from(assessmentService.submit(principal.getId(), req))
        );
    }

    @GetMapping
    public ResponseEntity<List<AssessmentResponse>> all(@AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.ok(
                assessmentService.getAll(principal.getId())
                        .stream()
                        .map(AssessmentResponse::from)
                        .toList()
        );
    }

    @GetMapping("/latest")
    public ResponseEntity<AssessmentResponse> latest(@AuthenticationPrincipal UserPrincipal principal) {
        Assessment a = assessmentService.getLatest(principal.getId());
        return ResponseEntity.ok(a == null ? null : AssessmentResponse.from(a));
    }

    @GetMapping("/by-skill")
    public ResponseEntity<Map<String, AssessmentResponse>> bySkill(@AuthenticationPrincipal UserPrincipal principal) {
        Map<String, Assessment> raw = assessmentService.getBySkill(principal.getId());

        Map<String, AssessmentResponse> mapped = raw.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> AssessmentResponse.from(e.getValue())
                ));

        return ResponseEntity.ok(mapped);
    }
}