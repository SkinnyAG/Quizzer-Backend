package edu.ntnu.fullstack.prosjekt.quizzer.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.dto.*;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.entities.QuizEntity;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.entities.UserEntity;
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
   */
  QuizDetailsDto createQuiz(QuizDetailsDto quizDetailsDto, UserEntity userEntity);

  /**
   * Service for finding a page of quizzes in the database.
   *
   * @return A page of quizzes
   */
  Page<QuizGeneralDto> findPageOfQuizzes(Pageable pageable);


  /**
   * Service for finding a page of quizzes in the database based on a search query.
   * @param searchQuery The search query.
   * @param pageable The pageable object.
   * @return A page of quizzes.
   */
  Page<QuizGeneralDto> filterQuizzes(String searchQuery, Pageable pageable);




  /**
   * Service for finding a specific quiz given an id.
   *
   * @param quizId Unique identifier for the quiz.
   * @return The quiz as a dto.
   */
  QuizDetailsDto findQuizDtoById(String quizId);


  /**
   * Service for finding all quiz categories.
   * @return A list of all categories.
   */
  List<CategoryDto> findAllCategories();

  /**
   * Service for finding a specific quiz given an id.
   *
   * @param quizId Unique identifier for the quiz.
   * @return The quiz as an entity.
   */
  QuizEntity findQuizEntityById(String quizId);

  /**
   * Service for updating a quiz in the database.
   * @param quizDetailsDto
   */
  void updateQuizEntity(QuizDetailsDto quizDetailsDto);

  /**
   * Service for finding a detailed quiz given an id.
   * @param quizId
   * @return
   */
  QuizDetailsDto findQuizDetails(String quizId);

  /**
   * Service for checking the answers of a quiz attempt.
   * @param quizId
   * @param quizAttemptDto
   * @param userEntity
   * @return
   */
  QuizAttemptDto checkAnswers(String quizId, QuizAttemptDto quizAttemptDto, UserEntity userEntity);
}
