package edu.ntnu.fullstack.prosjekt.quizzer.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * QuizAttemptDto is a data transfer object that represents a quiz attempt.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizAttemptDto {
  /**
   * The score field represents the score the user got on the quiz attempt.
   */
  private Integer score = 0;

  /**
   * The duration field represents the time the user used on the quiz attempt.
   */
  private Integer duration;

  /**
   * The questionAttempts field represents the question attempts in the quiz attempt.
   */
  private List<QuestionAttemptDto> questionAttempts;
}
