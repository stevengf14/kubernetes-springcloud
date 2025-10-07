package ec.com.learning.sprongcloud.msvc.courses.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "courses")
@Data
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CourseUser> courseUsers;

    public Course() {
        courseUsers = new ArrayList<>();
    }

    public void addCourseUser(CourseUser courseUser) {
        this.courseUsers.add(courseUser);
    }

    public void removeCourseUser(CourseUser courseUser) {
        this.courseUsers.remove(courseUser);
    }

}
