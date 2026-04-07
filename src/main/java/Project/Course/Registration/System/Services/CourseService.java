package Project.Course.Registration.System.Services;

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

    public List<CourseRegistry> enrolledStudents() {
        return courseRegistryRepository.findAll();
    }

    public CourseRegistry enrollCourse(String username, String email, Integer courseId) {

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        CourseRegistry courseRegistry = new CourseRegistry(username, email, course);
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
}
