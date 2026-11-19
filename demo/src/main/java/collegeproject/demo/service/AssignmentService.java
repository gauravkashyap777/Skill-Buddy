package collegeproject.demo.service;

import collegeproject.demo.entity.Assignment;
import collegeproject.demo.repository.AssignmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssignmentService {

    private final AssignmentRepository assignmentRepo;

    public AssignmentService(AssignmentRepository assignmentRepo) {
        this.assignmentRepo = assignmentRepo;
    }

    public List<Assignment> byCourse(Long courseId) {
        return assignmentRepo.findByCourseId(courseId);
    }

    public Assignment create(Assignment assignment) {
        return assignmentRepo.save(assignment);
    }
}