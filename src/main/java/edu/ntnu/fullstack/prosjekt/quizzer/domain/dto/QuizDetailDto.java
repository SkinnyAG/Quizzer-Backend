package edu.ntnu.fullstack.prosjekt.quizzer.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * A dto returned to users including information about quiz title, description,
 * image link, owner and a list of question dtos.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizDetailDto {
  private Long quizId;

  private String title;

  private String description;

  private String imageLink;

  private List<QuestionDto> questions;

  private UserDto owner;
}
