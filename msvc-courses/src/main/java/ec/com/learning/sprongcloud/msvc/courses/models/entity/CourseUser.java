package ec.com.learning.sprongcloud.msvc.courses.models.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Objects;

@Entity
@Table(name = "course_user")
@Data
public class CourseUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", unique = true)
    private Long userId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CourseUser)) return false;
        CourseUser that = (CourseUser) o;
        return this.userId != null && this.userId.equals(that.getUserId());
    }

}
