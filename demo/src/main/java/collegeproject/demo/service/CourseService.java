package collegeproject.demo.service;

import collegeproject.demo.entity.Course;
import collegeproject.demo.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepo;

    public CourseService(CourseRepository courseRepo) {
        this.courseRepo = courseRepo;
    }

    public List<Course> getAll() {
        return courseRepo.findAll();
    }

    public Course create(Course course) {
        return courseRepo.save(course);
    }

    public Course get(Long id) {
        return courseRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
    }

    public Course course(Course course) {
        return courseRepo.save(course);
    }
}