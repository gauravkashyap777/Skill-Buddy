package collegeproject.demo.controller;

import collegeproject.demo.dto.FeedbackRequest;
import collegeproject.demo.dto.FeedbackResponse;
import collegeproject.demo.entity.Feedback;
import collegeproject.demo.security.UserPrincipal;
import collegeproject.demo.service.FeedbackService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @PostMapping
    public ResponseEntity<FeedbackResponse> submit(@AuthenticationPrincipal UserPrincipal principal,
                                                   @RequestBody FeedbackRequest req) {
        Feedback saved = feedbackService.submit(principal.getId(), req);
        return ResponseEntity.ok(FeedbackResponse.from(saved));
    }

    @GetMapping
    public ResponseEntity<List<FeedbackResponse>> all(@AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.ok(
                feedbackService.getAll(principal.getId())
                        .stream()
                        .map(FeedbackResponse::from)
                        .toList()
        );
    }
}