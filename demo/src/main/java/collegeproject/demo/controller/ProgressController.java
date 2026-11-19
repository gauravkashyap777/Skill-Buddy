package collegeproject.demo.controller;

import collegeproject.demo.dto.ProgressRequest;
import collegeproject.demo.dto.ProgressResponse;
import collegeproject.demo.security.UserPrincipal;
import collegeproject.demo.service.ProgressService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/progress")
public class ProgressController {

    private final ProgressService progressService;

    public ProgressController(ProgressService progressService) {
        this.progressService = progressService;
    }

    @PostMapping("/update")
    public ResponseEntity<ProgressResponse> update(@AuthenticationPrincipal UserPrincipal principal,
                                                   @RequestBody ProgressRequest req) {
        return ResponseEntity.ok(
                ProgressResponse.from(progressService.update(principal.getId(), req))
        );
    }

    @GetMapping
    public ResponseEntity<List<ProgressResponse>> all(@AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.ok(
                progressService.getAll(principal.getId())
                        .stream()
                        .map(ProgressResponse::from)
                        .toList()
        );
    }

    @GetMapping("/{skill}")
    public ResponseEntity<ProgressResponse> one(@AuthenticationPrincipal UserPrincipal principal,
                                                @PathVariable String skill) {
        var p = progressService.get(principal.getId(), skill);
        return ResponseEntity.ok(p == null ? null : ProgressResponse.from(p));
    }
}