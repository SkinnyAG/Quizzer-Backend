package edu.ntnu.fullstack.prosjekt.quizzer.domain.dto;

import edu.ntnu.fullstack.prosjekt.quizzer.domain.entities.QuizEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The UserDto class is a mirror of the userEntity class, with the intention of creating
 * a separation between user input/output and database objects.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDto {
  /**
   * The questionId field is a unique identifier and primary key for a question entry in
   * the database.
   */
  private Long questionId;

  /**
   * The label field represents the question that is being asked, such as "How many people live
   * in Norway?."
   */
  private String label;

  /**
   * The position field should tell the question which position it will have in an ordered quiz,
   * which will be randomized if wanted.
   */
  private Short position;

  /**
   * The alternatives field should store JSON with the different alternatives that
   * a user can answer.
   */
  private String alternatives;

  /**
   * The quiz field references which quiz the question belongs to, in a many questions to
   * one quiz relationship.
   */
  private QuizEntity quiz;
}
