package ec.com.learning.sprongcloud.msvc.courses.controllers;

import ec.com.learning.sprongcloud.msvc.courses.models.entity.Course;
import ec.com.learning.sprongcloud.msvc.courses.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CourseController {

    @Autowired
    private CourseService service;

    @GetMapping
    public ResponseEntity<List<Course>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Optional<Course> opt = service.getById(id);
        if (opt.isPresent()) {
            return ResponseEntity.ok(opt.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/")
    public ResponseEntity<?> save(@RequestBody Course course) {
        Course courseDb = service.save(course);
        return ResponseEntity.status(HttpStatus.CREATED).body(courseDb);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Course course, @PathVariable Long id) {
        Optional<Course> opt = service.getById(id);
        if (opt.isPresent()) {
            Course courseDb = opt.get();
            courseDb.setName(course.getName());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.save(courseDb));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<Course> opt = service.getById(id);
        if (opt.isPresent()) {
            service.delete(opt.get().getId());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}

