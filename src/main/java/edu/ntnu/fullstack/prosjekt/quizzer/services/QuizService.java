package edu.ntnu.fullstack.prosjekt.quizzer.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.dto.QuestionDto;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.dto.QuizDetailsDto;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.dto.QuizGeneralDto;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.entities.QuizEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Interface providing services between the quiz database table and user requests.
 */
public interface QuizService {
  /**
   * Service for creating a quiz in the database.
   *
   * @param quizDetailsDto QuizDetailsDto that should be added.
   * @return The created QuizDetailsDto.
   */
  QuizDetailsDto createQuiz(QuizDetailsDto quizDetailsDto);

  QuestionDto addQuestionToQuiz(String quizId, QuestionDto questionDto) throws JsonProcessingException;

  /**
   * Service for finding a page of quizzes in the database.
   *
   * @return A page of quizzes
   */
  Page<QuizGeneralDto> findPageOfQuizzes(Pageable pageable);

  /**
   * Service for finding a specific quiz given an id.
   *
   * @param quizId Unique identifier for the quiz.
   * @return The quiz as a dto.
   */
  QuizDetailsDto findQuizDtoById(String quizId);

  /**
   * Service for finding a specific quiz given an id.
   *
   * @param quizId Unique identifier for the quiz.
   * @return The quiz as an entity.
   */
  QuizEntity findQuizEntityById(String quizId);

  void updateQuizEntity(QuizDetailsDto quizDetailsDto);

  QuizDetailsDto findQuizDetails(String quizId);
}
