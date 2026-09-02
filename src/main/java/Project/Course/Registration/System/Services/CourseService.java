package Project.Course.Registration.System.Services;

import Project.Course.Registration.System.DTO.CourseRegistryResponse;
import Project.Course.Registration.System.Entity.Course;
import Project.Course.Registration.System.Entity.CourseRegistry;
import Project.Course.Registration.System.Repository.CourseRegistryRepository;
import Project.Course.Registration.System.Repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    CourseRegistryRepository courseRegistryRepository;

    public List<Course> fetchAllCourses() {
        return courseRepository.findAll();
    }

    public CourseRegistry enrollCourse(String username, String email, Integer courseId) {
        // 1. Fetch the Course
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with ID: " + courseId));

        // 2. Create the Registry
        // We use the constructor that takes username, email, and Course object
        CourseRegistry courseRegistry = new CourseRegistry(username, email, course);

        // 3. Save
        return courseRegistryRepository.save(courseRegistry);
    }

    public void addCourse(Course course) {
        courseRepository.save(course);
    }

    public void addMultipleCourses(List<Course> course) {
        courseRepository.saveAll(course);
    }

    public Course getCourseWithStudents(Integer courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
    }

    public CourseRegistry getCourse(Integer courseId) {
        return courseRegistryRepository.findById(courseId)
                .orElseThrow(()-> new RuntimeException("CourseRegistry not found"));
    }

    public List<CourseRegistryResponse> enrolledStudents() {

        return courseRegistryRepository.findAll()
                .stream()
                .map(registry -> {

                    CourseRegistryResponse dto = new CourseRegistryResponse();

                    dto.setId(registry.getId());
                    dto.setUsername(registry.getUsername());
                    dto.setEmail(registry.getEmail());

                    if (registry.getCourse() != null) {
                        dto.setCourseName(registry.getCourse().getCourseName());
                    }

                    return dto;
                })
                .toList();
    }
}
