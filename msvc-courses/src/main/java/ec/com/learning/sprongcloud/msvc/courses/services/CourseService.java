package ec.com.learning.sprongcloud.msvc.courses.services;

import ec.com.learning.sprongcloud.msvc.courses.models.entity.Course;

import java.util.List;
import java.util.Optional;

public interface CourseService {

    List<Course> getAll();

    Optional<Course> getById(Long id);

    Course save(Course course);

    void delete(Long id);

}
