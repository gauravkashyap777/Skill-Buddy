package collegeproject.demo.controller;

import collegeproject.demo.entity.Assignment;
import collegeproject.demo.repository.EnrollmentRepository;
import collegeproject.demo.security.UserPrincipal;
import collegeproject.demo.service.AssignmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {

    private final AssignmentService assignmentService;
    private final EnrollmentRepository enrollmentRepo;

    public AssignmentController(AssignmentService assignmentService,
                                EnrollmentRepository enrollmentRepo) {
        this.assignmentService = assignmentService;
        this.enrollmentRepo = enrollmentRepo;
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<?> assignmentsForCourse(@AuthenticationPrincipal UserPrincipal principal,
                                                  @PathVariable Long courseId) {
        boolean enrolled = enrollmentRepo.existsByUserIdAndCourseId(principal.getId(), courseId);

        if (!enrolled) {
            return ResponseEntity.status(403)
                    .body(Map.of("error", "You are not enrolled in this course"));
        }

        List<Assignment> list = assignmentService.byCourse(courseId);
        return ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity<Assignment> create(@RequestBody Assignment assignment) {
        return ResponseEntity.ok(assignmentService.create(assignment));
    }
}