package edu.ntnu.fullstack.prosjekt.quizzer.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.dto.QuestionDto;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.dto.QuizDetailsDto;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.entities.QuestionEntity;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.entities.QuizEntity;

import java.util.List;

/**
 * Interface providing services between the question database table and user requests.
 */
public interface QuestionService {
  /**
   * Service for creating a question in the database.
   *
   * @param questionDto QuestionDto that should be added.
   * @return The created QuestionDto.
   */
  QuestionDto createQuestion(QuizEntity quizEntity, QuestionDto questionDto) throws JsonProcessingException;

  List<QuestionDto> getQuestionsByQuiz(QuizEntity quizEntity);

  List<QuestionEntity> getQuestionEntitiesByQuiz(QuizEntity quizEntity);

  int getAmountOfQuestionsByQuiz(QuizEntity quizEntity);

  void deleteQuestionsByQuizEntity(QuizEntity quizEntity);

  void addListOfQuestions(List<QuestionDto> questionDtos, QuizEntity quizEntity);
}
