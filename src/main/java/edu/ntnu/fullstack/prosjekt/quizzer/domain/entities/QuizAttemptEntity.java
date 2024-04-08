package edu.ntnu.fullstack.prosjekt.quizzer.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * QuizAttemptEntity is an entity that represents a user's attempt at a quiz in the database.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizAttemptEntity {
  /**
   * The attemptId field is a unique identifier and primary key for a quiz attempt entry in the database.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "attempt_id_seq")
  private Long attemptId;

  /**
   * The score field represents the score the user got on the quiz attempt.
   */
  private Integer score;


    /**
     * The duration field represents the time the user used on the quiz attempt.
     */
  private Integer duration;

    /**
     * The user field represents the user that attempted the quiz.
     */
  @ManyToOne
  private UserEntity user;

  /**
   * The quiz field represents the quiz that was attempted.
   */
  @ManyToOne
  private QuizEntity quiz;
}
