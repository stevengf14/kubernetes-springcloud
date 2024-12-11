package ec.com.learning.sprongcloud.msvc.users.repositories;

import ec.com.learning.sprongcloud.msvc.users.models.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
