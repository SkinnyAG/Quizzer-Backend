package edu.ntnu.fullstack.prosjekt.quizzer.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * QuestionAnswersDto is a data transfer object that represents a question with answers.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionAnswersDto {

  /**
   * The answer field represents the answer label for the answer.
   */
  private String answer;

  /**
   * The isCorrect field represents whether the answer is correct or not.
   */
  private Boolean isCorrect;
}
