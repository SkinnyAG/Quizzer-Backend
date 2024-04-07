package edu.ntnu.fullstack.prosjekt.quizzer.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SavedQuizAttemptDto {

  private Integer score;

  private Integer duration;

  private QuizGeneralDto quiz;
}
