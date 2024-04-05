package edu.ntnu.fullstack.prosjekt.quizzer.services.impl;

import edu.ntnu.fullstack.prosjekt.quizzer.domain.dto.QuestionDto;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.entities.QuestionEntity;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.entities.QuizEntity;
import edu.ntnu.fullstack.prosjekt.quizzer.mappers.Mapper;
import edu.ntnu.fullstack.prosjekt.quizzer.repositories.QuestionRepository;
import edu.ntnu.fullstack.prosjekt.quizzer.services.QuestionService;
import edu.ntnu.fullstack.prosjekt.quizzer.services.QuizService;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

/**
 * A class implementing the methods specified in its interface.
 * Provides services between user requests and database operations.
 */
@Service
@Log
public class QuestionServiceImpl implements QuestionService {
  /**
   * Used for Dependency Injection.
   */
  private QuestionRepository questionRepository;

  private QuizService quizService;

  private Mapper<QuestionEntity, QuestionDto> questionMapper;

  /**
   * Used for Dependency Injection.
   *
   * @param questionRepository The injected QuestionRepository object.
   */
  public QuestionServiceImpl(QuestionRepository questionRepository, QuizService quizService,
                             Mapper<QuestionEntity,
          QuestionDto> questionMapper) {
    this.questionRepository = questionRepository;
    this.quizService = quizService;
    this.questionMapper = questionMapper;
  }

  /**
   * Creates a question in the database table.
   *
   * @param questionDto QuestionDto that should be added.
   * @return The created question as a dto.
   */
  @Override
  public QuestionDto createQuestion(QuestionDto questionDto) {
    if (questionDto.getLabel() == null || questionDto.getLabel().isEmpty()) {
      throw new IllegalArgumentException("Undefined question label");
    }
    QuizEntity quizEntity = quizService.findQuizEntityById(questionDto.getQuizId());
    if (quizEntity == null) {
      throw new IllegalArgumentException("Quiz is not defined");
    }
    QuestionEntity questionEntity = questionMapper.mapFrom(questionDto);
    questionEntity.setQuiz(quizEntity);

    questionRepository.save(questionEntity);
    return questionMapper.mapTo(questionEntity);
  }

}
