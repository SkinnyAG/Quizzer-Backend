package edu.ntnu.fullstack.prosjekt.quizzer.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * QuestionAttemptDto is a data transfer object that represents a question attempt for a
 * single answer
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionAttemptDto {
  /**
   * The questionId field represents the id of the question.
   */
  private Long questionId;

  /**
   * The questionLabel field represents answer that was chosen by the user
   */
  private String answerLabel;

  /**
   * The answeredCorrect field represents whether the user answered the question correctly.
   */
  private Boolean answeredCorrect = false;
}
