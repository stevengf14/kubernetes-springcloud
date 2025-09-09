package ec.com.learning.sprongcloud.msvc.courses.services;

import ec.com.learning.sprongcloud.msvc.courses.models.entity.Course;
import ec.com.learning.sprongcloud.msvc.courses.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Course> getAll() {
        return (List<Course>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Course> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Course save(Course course) {
        return repository.save(course);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
