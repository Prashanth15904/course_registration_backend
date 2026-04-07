package Project.Course.Registration.System.Repository;

import Project.Course.Registration.System.Entity.CourseRegistry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRegistryRepository extends JpaRepository<CourseRegistry,Integer> {

    List<CourseRegistry> findByCourse_CourseId(Integer courseId);
}
