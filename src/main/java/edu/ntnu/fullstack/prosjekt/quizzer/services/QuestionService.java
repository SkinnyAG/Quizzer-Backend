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

  /**
   * Service for finding questions belonging to a quiz.
   * @param quizEntity The quiz to find questions for.
   * @return A list of questions.
   */
  List<QuestionDto> getQuestionsByQuiz(QuizEntity quizEntity);

  /**
   * Service for finding amount of questions belonging to a quiz.
   * @param quizEntity The quiz to find questions for.
   * @return The amount of questions.
   */
  int getAmountOfQuestionsByQuiz(QuizEntity quizEntity);

  /**
   * Service for clearing all questions belonging to a quiz.
   * @param quizEntity The quiz to clear questions for.
   */
  void clearQuestionsByQuizEntity(QuizEntity quizEntity);


  /**
   * Service for adding a list of questions to a quiz.
   * @param questionDtos The list of questions to add.
   * @param quizEntity The quiz to add questions to.
   */
  void addListOfQuestions(List<QuestionDto> questionDtos, QuizEntity quizEntity);
}
