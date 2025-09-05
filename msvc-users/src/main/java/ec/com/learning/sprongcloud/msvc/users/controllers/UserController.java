package ec.com.learning.sprongcloud.msvc.users.controllers;

import ec.com.learning.sprongcloud.msvc.users.models.entity.User;
import ec.com.learning.sprongcloud.msvc.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
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
    public ResponseEntity<?> save(@RequestBody User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody User user, @PathVariable Long id) {
        Optional<User> optionalUser = service.findById(id);
        if (optionalUser.isPresent()) {
            User dbUser = optionalUser.get();
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
}
