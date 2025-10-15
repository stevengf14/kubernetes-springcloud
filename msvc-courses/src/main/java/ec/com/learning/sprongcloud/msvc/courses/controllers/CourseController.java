package ec.com.learning.sprongcloud.msvc.courses.controllers;

import ec.com.learning.sprongcloud.msvc.courses.models.User;
import ec.com.learning.sprongcloud.msvc.courses.models.entity.Course;
import ec.com.learning.sprongcloud.msvc.courses.services.CourseService;
import feign.FeignException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
    public ResponseEntity<?> save(@Valid @RequestBody Course course, BindingResult result) {
        if (result.hasErrors()) {
            return validate(result);
        }
        Course courseDb = service.save(course);
        return ResponseEntity.status(HttpStatus.CREATED).body(courseDb);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Course course, BindingResult result, @PathVariable Long id) {
        if (result.hasErrors()) {
            return validate(result);
        }
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

    @PutMapping("/assign-user/{courseId}")
    public ResponseEntity<?> assignUser(@RequestBody User user, @PathVariable Long courseId) {
        Optional<User> o;
        try {
            o = service.assignUser(user, courseId);
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("mensaje",
                            "No existe el usuario por el id o error en la comunicacion: " + e.getMessage()));
        }
        if (o.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/create-user/{courseId}")
    public ResponseEntity<?> createUser(@RequestBody User user, @PathVariable Long courseId) {
        Optional<User> o;
        try {
            o = service.createUser(user, courseId);
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("mensaje",
                            "No se pudo crear el usuario o error en la comunicacion: " + e.getMessage()));
        }
        if (o.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/remove-user/{courseId}")
    public ResponseEntity<?> removeUser(@RequestBody User user, @PathVariable Long courseId) {
        Optional<User> o;
        try {
            o = service.removeUser(user, courseId);
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("mensaje",
                            "No existe el usuario por el id o error en la comunicacion: " + e.getMessage()));
        }
        if (o.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(o.get());
        }
        return ResponseEntity.notFound().build();
    }

    private static ResponseEntity<Map<String, String>> validate(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(error -> {
            errors.put(error.getField(), "El campo " + error.getField() + " " + error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }

}

