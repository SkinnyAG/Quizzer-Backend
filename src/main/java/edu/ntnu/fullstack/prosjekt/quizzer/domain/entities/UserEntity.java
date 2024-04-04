package edu.ntnu.fullstack.prosjekt.quizzer.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UserEntity is one of the main entities in the application, acting as users saved in the database.
 * They can either play or create quizzes.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {

  /**
   * The username field is a unique identifier for a user, and its primary key in the database.
   */
  @Id
  private String username;

  /**
   * The fullName field represents the full name of the user.
   */
  private String fullName;

  /**
   * The email field represents the email of the user.
   */
  private String email;

  /**
   * The password field represents the salted and hashed password of the user.
   */
  private String password;

  /**
   * The quizzes field represents the quizzes a user has created, with a user being
   * able to have many quizzes in a 'one to many' relationship.
   */
  @OneToMany (mappedBy = "owner")
  private List<QuizEntity> quizzes;
}
