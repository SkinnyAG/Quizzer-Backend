package edu.ntnu.fullstack.prosjekt.quizzer.domain.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * QuizEntity is one of the main entities in the application, acting as quizzes owned by users in
 * the database, and containing questions.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "quizzes")
public class QuizEntity {

  /**
   * The quizId field is a unique identifier and primary key for a quiz entry in
   * the database.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quiz_id_seq")
  private Long quizId;

  /**
   * The title field represents the name of the quiz.
   */
  private String title;

  /**
   * The description field should be used for describing the quiz.
   */
  private String description;

  /**
   * Links to an image for the quiz.
   */
  private String imageLink = "";

  /**
   * The owner field should represent the user who owns the quiz, with many quizzes belonging to
   * one user. This field is a foreign key linking owner and quizzes.
   */
  @ManyToOne
  @JsonBackReference
  @JoinColumn(name = "username")
  private UserEntity owner;
}
