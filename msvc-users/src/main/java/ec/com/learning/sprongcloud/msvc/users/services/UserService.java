package ec.com.learning.sprongcloud.msvc.users.services;

import ec.com.learning.sprongcloud.msvc.users.models.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> findAll();

    Optional<User> findById(Long id);

    User save(User user);

    void delete(User user);

    List<User> findAllById(Iterable<Long> ids);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

}
