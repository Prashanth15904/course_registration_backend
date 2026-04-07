package Project.Course.Registration.System.Controller;


import Project.Course.Registration.System.Entity.Course;
import Project.Course.Registration.System.Entity.CourseRegistry;
import Project.Course.Registration.System.Services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = {
        "http://localhost:5500",
        "http://127.0.0.1:5500"
})
@RestController
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping("/courses")
    public List<Course> availableCourse(){
        return courseService.fetchAllCourses();
    }

    @PostMapping("/course/register")
    public ResponseEntity<CourseRegistry> registerCourse(@RequestParam String username, @RequestParam String email, @RequestParam Integer courseId) {
        try {
            CourseRegistry registry = courseService.enrollCourse(username, email, courseId);
            return ResponseEntity.ok(registry);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null); // or return error message
        }
    }

    @PostMapping("/course/addCourse")
    public String addCourse(@RequestBody Course course){
        courseService.addCourse(course);
        return "Course Added To Database";
    }

    @PostMapping("/course/addMultipleCourses")
    public String addMultipleCourses(@RequestBody  List<Course> course){
        courseService.addMultipleCourses(course);
        return "Multiple Course Added To Database";
    }

    @GetMapping("/courses/{courseId}/details")
    public Course getCourseDetails(@PathVariable Integer courseId) {
        return courseService.getCourseWithStudents(courseId);
    }

    @GetMapping("/courses/{courseId}")
    public CourseRegistry getCourseDetail(@PathVariable Integer courseId) {
        return courseService.getCourse(courseId);
    }
}
