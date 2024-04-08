package edu.ntnu.fullstack.prosjekt.quizzer.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * SavedQuizAttemptDto is a data transfer object that represents a saved quiz attempt.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SavedQuizAttemptDto {

  /**
   * The score field represents the score the user got on the quiz attempt.
   */
  private Integer score;

  /**
   * The duration field represents the time the user used on the quiz attempt.
   */
  private Integer duration;

  /**
   * The quiz field represents the quiz that was attempted.
   */
  private QuizGeneralDto quiz;
}
