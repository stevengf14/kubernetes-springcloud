package ec.com.learning.sprongcloud.msvc.courses.repositories;

import ec.com.learning.sprongcloud.msvc.courses.models.entity.Course;
import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<Course, Long> {
}
