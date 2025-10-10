package ec.com.learning.sprongcloud.msvc.courses.clients;

import ec.com.learning.sprongcloud.msvc.courses.models.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "msvc-users", url = "localhost:8001") //path = "/users")
public interface UserClientRest {

    @GetMapping("/{id}")
    User findById(@PathVariable Long id);

    @PostMapping("/")
    User save(@RequestBody User user);

}
