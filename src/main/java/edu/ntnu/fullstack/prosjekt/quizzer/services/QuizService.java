package edu.ntnu.fullstack.prosjekt.quizzer.services;

import edu.ntnu.fullstack.prosjekt.quizzer.domain.dto.QuizDto;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.entities.QuizEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Interface providing services between the quiz database table and user requests.
 */
public interface QuizService {
  /**
   * Service for creating a quiz in the database.
   *
   * @param quizDto QuizDto that should be added.
   * @return The created QuizDto.
   */
  QuizDto createQuiz(QuizDto quizDto);

  /**
   * Service for finding a page of quizzes in the database.
   *
   * @return A page of quizzes
   */
  Page<QuizDto> findPageOfQuizzes(Pageable pageable);

  /**
   * Service for finding a specific quiz given an id.
   *
   * @param quizId Unique identifier for the quiz.
   * @return The quiz as a dto.
   */
  QuizDto findQuizDtoById(String quizId);

  /**
   * Service for finding a specific quiz given an id.
   *
   * @param quizId Unique identifier for the quiz.
   * @return The quiz as an entity.
   */
  QuizEntity findQuizEntityById(String quizId);

}
