package edu.ntnu.fullstack.prosjekt.quizzer.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    private String username;

    private String fullName;

    private String email;

    private String password;

    @OneToMany (mappedBy = "owner")
    private List<QuizEntity> quizzes;
}
