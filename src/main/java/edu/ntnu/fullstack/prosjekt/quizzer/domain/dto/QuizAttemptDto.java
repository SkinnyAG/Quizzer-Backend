package edu.ntnu.fullstack.prosjekt.quizzer.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizAttemptDto {
  private Integer score = 0;

  private Integer duration;

  private List<QuestionAttemptDto> questionAttempts;
}
