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
  /**
   * The quizId field is a unique identifier and primary key for a quiz entry in
   * the database.
   */
  private Long quizId;

  /**
   * The title field represents the name of the quiz.
   */
  private String title;

  /**
   * The description field should be used for describing the quiz.
   */
  private String description;

  /**
   * Links to an image for the quiz.
   */
  private String imageLink;

  /**
   * The amountOfQuestions field represents the amount of questions in the quiz.
   */
  private int amountOfQuestions;

  /**
   * The categories field should store a list of categories that the quiz belongs to.
   */
  private List<CategoryDto> categories = new ArrayList<>();

  private UserDto owner;
}
