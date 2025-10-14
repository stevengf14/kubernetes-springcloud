package ec.com.learning.sprongcloud.msvc.courses.services;

import ec.com.learning.sprongcloud.msvc.courses.clients.UserClientRest;
import ec.com.learning.sprongcloud.msvc.courses.models.User;
import ec.com.learning.sprongcloud.msvc.courses.models.entity.Course;
import ec.com.learning.sprongcloud.msvc.courses.models.entity.CourseUser;
import ec.com.learning.sprongcloud.msvc.courses.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository repository;

    @Autowired
    private UserClientRest client;

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

    @Override
    @Transactional
    public Optional<User> assignUser(User user, Long courseId) {
        Optional<Course> o = repository.findById(courseId);
        if (o.isPresent()) {
            User userMsvc = client.findById(user.getId());

            Course course = o.get();
            CourseUser courseUser = new CourseUser();
            courseUser.setUserId(userMsvc.getId());

            course.addCourseUser(courseUser);
            repository.save(course);
            return Optional.of(userMsvc);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<User> createUser(User user, Long courseId) {
        Optional<Course> o = repository.findById(courseId);
        if (o.isPresent()) {
            User newUserMsvc = client.save(user);

            Course course = o.get();
            CourseUser courseUser = new CourseUser();
            courseUser.setUserId(newUserMsvc.getId());

            course.addCourseUser(courseUser);
            repository.save(course);
            return Optional.of(newUserMsvc);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<User> removeUser(User user, Long courseId) {
        Optional<Course> o = repository.findById(courseId);
        if (o.isPresent()) {
            User userMsvc = client.findById(user.getId());

            Course course = o.get();
            CourseUser courseUser = new CourseUser();
            courseUser.setUserId(userMsvc.getId());

            course.removeCourseUser(courseUser);
            repository.save(course);
            return Optional.of(userMsvc);
        }
        return Optional.empty();
    }
}
