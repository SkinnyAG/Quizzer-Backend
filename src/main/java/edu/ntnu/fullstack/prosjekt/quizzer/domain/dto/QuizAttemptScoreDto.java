package edu.ntnu.fullstack.prosjekt.quizzer.domain.dto;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class QuizAttemptScoreDto {
  private Long attemptId;

  private Integer score;

  private Integer timeUsed;

  private UserDto user;

  private QuizAttemptDto quizAttempt;
}
