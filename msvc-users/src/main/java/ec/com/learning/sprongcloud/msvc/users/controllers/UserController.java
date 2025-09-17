package ec.com.learning.sprongcloud.msvc.users.controllers;

import ec.com.learning.sprongcloud.msvc.users.models.entity.User;
import ec.com.learning.sprongcloud.msvc.users.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping
    public List<User> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<User> user = service.findById(id);
        return user.isPresent() ? ResponseEntity.ok(user.get()) : ResponseEntity.noContent().build();
    }

//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public User save(@RequestBody User user) {
//        return service.save(user);
//    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody User user, BindingResult result) {
        if (result.hasErrors()) {
            return validate(result);
        }
        if (!user.getEmail().isEmpty() && service.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", "Ya existe un usuario con ese correo"));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody User user, BindingResult result, @PathVariable Long id) {
        if (result.hasErrors()) {
            return validate(result);
        }
        Optional<User> optionalUser = service.findById(id);
        if (optionalUser.isPresent()) {
            User dbUser = optionalUser.get();
            if (!user.getEmail().isEmpty() &&
                    !user.getEmail().equalsIgnoreCase(dbUser.getEmail()) &&
                    service.findByEmail(user.getEmail()).isPresent()) {
                return ResponseEntity.badRequest()
                        .body(Map.of("mensaje", "Ya existe un usuario con ese correo"));
            }

            dbUser.setName(user.getName());
            dbUser.setEmail(user.getEmail());
            dbUser.setPassword(user.getPassword());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dbUser));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<User> optionalUser = service.findById(id);
        if (optionalUser.isPresent()) {
            service.delete(optionalUser.get());
            return ResponseEntity.noContent().build();
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
