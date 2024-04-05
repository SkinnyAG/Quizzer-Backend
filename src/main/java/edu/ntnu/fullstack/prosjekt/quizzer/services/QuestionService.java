package edu.ntnu.fullstack.prosjekt.quizzer.services;

import edu.ntnu.fullstack.prosjekt.quizzer.domain.dto.QuestionDto;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.dto.QuizDto;
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
  QuestionDto createQuestion(QuizEntity quizEntity, QuestionDto questionDto);

  List<QuestionDto> getQuestionsByQuiz(QuizEntity quizEntity);

}
