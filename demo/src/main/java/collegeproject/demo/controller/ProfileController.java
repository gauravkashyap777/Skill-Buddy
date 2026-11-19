package collegeproject.demo.controller;

import collegeproject.demo.dto.AssessmentResponse;
import collegeproject.demo.dto.CertificateResponse;
import collegeproject.demo.dto.ProgressResponse;
import collegeproject.demo.security.UserPrincipal;
import collegeproject.demo.service.AssessmentService;
import collegeproject.demo.service.CertificateService;
import collegeproject.demo.service.ProgressService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    private final AssessmentService assessmentService;
    private final CertificateService certificateService;
    private final ProgressService progressService;

    public ProfileController(AssessmentService assessmentService,
                             CertificateService certificateService,
                             ProgressService progressService) {
        this.assessmentService = assessmentService;
        this.certificateService = certificateService;
        this.progressService = progressService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> profile(@AuthenticationPrincipal UserPrincipal principal) {

        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("id", principal.getId());
        userInfo.put("username", principal.getUsername());
        userInfo.put("email", principal.getEmail());
        userInfo.put("role", principal.getRole());

        Map<String, Object> map = new HashMap<>();
        map.put("user", userInfo);
        map.put("assessments", assessmentService.getAll(principal.getId())
                .stream().map(AssessmentResponse::from).toList());
        map.put("certificates", certificateService.getAll(principal.getId())
                .stream().map(CertificateResponse::from).toList());
        map.put("progress", progressService.getAll(principal.getId())
                .stream().map(ProgressResponse::from).toList());

        return ResponseEntity.ok(map);
    }
}