package edu.ntnu.fullstack.prosjekt.quizzer.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Dto returned from controller containing basic quiz information such as
 * title, description, owner data, image link and quiz id.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizGeneralDto {
  private Long quizId;

  private String title;

  private String description;

  private String imageLink;

  private int amountOfQuestions;

  private List<CategoryDto> categories = new ArrayList<>();

  private UserDto owner;
}
