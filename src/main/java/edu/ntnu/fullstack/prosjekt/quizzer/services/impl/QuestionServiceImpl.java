package edu.ntnu.fullstack.prosjekt.quizzer.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.dto.QuestionAnswersDto;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.dto.QuestionDto;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.dto.QuizDetailsDto;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.entities.QuestionEntity;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.entities.QuizEntity;
import edu.ntnu.fullstack.prosjekt.quizzer.mappers.Mapper;
import edu.ntnu.fullstack.prosjekt.quizzer.repositories.QuestionRepository;
import edu.ntnu.fullstack.prosjekt.quizzer.services.QuestionService;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.List;

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

  private ObjectMapper objectMapper;


  private Mapper<QuestionEntity, QuestionDto> questionMapper;

  /**
   * Used for Dependency Injection.
   *
   * @param questionRepository The injected QuestionRepository object.
   */
  public QuestionServiceImpl(QuestionRepository questionRepository,
                             Mapper<QuestionEntity, QuestionDto> questionMapper,
                             ObjectMapper objectMapper) {
    this.questionRepository = questionRepository;
    this.questionMapper = questionMapper;
    this.objectMapper = objectMapper;
  }

  /**
   * Creates a question in the database table.
   *
   * @param questionDto QuestionDto that should be added.
   * @return The created question as a dto.
   */
  @Override
  public QuestionDto createQuestion(QuizEntity quizEntity, QuestionDto questionDto) {
    if (questionDto.getLabel() == null || questionDto.getLabel().isEmpty()) {
      throw new IllegalArgumentException("Undefined question label");
    }
    if (quizEntity == null) {
      throw new IllegalArgumentException("Quiz is not defined");
    }
    QuestionEntity questionEntity = questionMapper.mapFrom(questionDto);
    questionEntity.setQuiz(quizEntity);

    mapToJson(questionDto.getAlternatives(), questionEntity);

    QuestionEntity savedQuestionEntity = questionRepository.save(questionEntity);
    QuestionDto savedQuestionDto = questionMapper.mapTo(savedQuestionEntity);

    mapFromJson(savedQuestionEntity);

    log.info("Saved question entity: " + savedQuestionEntity);
    log.info("Saved question as dto: " + questionMapper.mapTo(savedQuestionEntity));
    return savedQuestionDto;
  }

  @Override
  public List<QuestionDto> getQuestionsByQuiz(QuizEntity quizEntity) {
    List<QuestionEntity> questionEntities = questionRepository.findQuestionEntitiesByQuiz(quizEntity);
    log.info("Before mapping to dot");
    List<QuestionDto> questionDtos = questionEntities.stream().map(questionEntity -> questionMapper
            .mapTo(questionEntity)).toList();
    log.info("After mapping to dto: " + questionDtos);
    return questionDtos;
  }

  public List<QuestionEntity> getQuestionEntitiesByQuiz(QuizEntity quizEntity) {
    List<QuestionEntity> questions = questionRepository.findQuestionEntitiesByQuiz(quizEntity);
    return questions;
  }

  @Override
  public int getAmountOfQuestionsByQuiz(QuizEntity quizEntity) {
    return questionRepository.findQuestionEntitiesByQuiz(quizEntity).size();
  }

  @Override
  public void clearQuestionsByQuizEntity(QuizEntity quizEntity) {
    List<QuestionEntity> questionEntities = questionRepository.findQuestionEntitiesByQuiz(quizEntity);
    for (QuestionEntity questionEntity : questionEntities) {
      questionRepository.deleteById(questionEntity.getQuestionId());
    }
  }

  @Override
  public void addListOfQuestions(List<QuestionDto> questionDtos, QuizEntity quizEntity) {
    log.info("Starting to add questions");
    for (QuestionDto questionDto : questionDtos) {
      log.info("Adding");
      log.info("QuizEntity: " + quizEntity);
      log.info("Quesitondto: " + questionDto);
      createQuestion(quizEntity, questionDto);
    }
  }


  public QuestionDto mapFromJson(QuestionEntity questionEntity) {
    QuestionDto questionDto = questionMapper.mapTo(questionEntity);
    if (questionEntity.getAlternatives() != null) {
      try {
        questionDto.setAlternatives(objectMapper.readValue(questionEntity.getAlternatives(), List.class));
        return questionDto;
      } catch (JsonProcessingException e) {
        throw new RuntimeException(e);
      }
    }
    return null;
  }

  public QuestionEntity mapToJson(List<QuestionAnswersDto> questions, QuestionEntity questionEntity) {
    if (questions != null && questionEntity != null) {
      try {
        questionEntity.setAlternatives(objectMapper.writeValueAsString(questions));
        return questionEntity;
      } catch (JsonProcessingException e) {
        throw new RuntimeException(e);
      }
    }
    return null;
  }

}
