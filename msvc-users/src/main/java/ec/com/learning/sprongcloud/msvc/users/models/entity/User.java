package ec.com.learning.sprongcloud.msvc.users.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank//(message = "Name cannot be empty")
    private String name;

    @Email
    @NotEmpty
    @Column(unique = true)
    private String email;

    @NotBlank
    private String password;

}
