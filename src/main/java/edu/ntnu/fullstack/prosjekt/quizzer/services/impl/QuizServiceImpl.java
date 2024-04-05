package edu.ntnu.fullstack.prosjekt.quizzer.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.dto.QuestionDto;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.dto.QuizDto;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.dto.UserDto;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.entities.QuizEntity;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.entities.UserEntity;
import edu.ntnu.fullstack.prosjekt.quizzer.mappers.Mapper;
import edu.ntnu.fullstack.prosjekt.quizzer.repositories.QuizRepository;
import edu.ntnu.fullstack.prosjekt.quizzer.services.QuestionService;
import edu.ntnu.fullstack.prosjekt.quizzer.services.QuizService;
import edu.ntnu.fullstack.prosjekt.quizzer.services.UserService;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * A class implementing the methods specified in its interface.
 * Provides services between user requests and database operations.
 */
@Service
@Log
public class QuizServiceImpl implements QuizService {
  /**
   * Used for Dependency Injection.
   */
  private QuizRepository quizRepository;

  /**
   * Used for Dependency Injection.
   */
  private Mapper<QuizEntity, QuizDto> quizMapper;

  /**
   * Used for Dependency Injection.
   */
  private UserService userService;

  private QuestionService questionService;


  /**
   * Used for Dependency Injection.
   *
   * @param quizRepository The Injected QuizRepository object.
   */
  public QuizServiceImpl(QuizRepository quizRepository, UserService userService,
                         QuestionService questionService, Mapper<QuizEntity, QuizDto> quizMapper) {
    this.quizRepository = quizRepository;
    this.quizMapper = quizMapper;
    this.userService = userService;
    this.questionService = questionService;
  }

  /**
   * Creates a quiz in the database table.
   *
   * @param quizDto QuizEntity that should be added.
   * @return The created QuizEntity
   */
  @Override
  public QuizDto createQuiz(QuizDto quizDto) {
    log.info("Creating quiz");

    if (quizDto.getTitle() == null || quizDto.getTitle().isEmpty()) {
      log.info("Undefined quiz title");
      throw new IllegalArgumentException("Undefined quiz title");
    }

    UserEntity userEntity = userService.findEntityByUsername(quizDto.getOwner());
    if (userEntity == null) {
      log.info("Could not find user");
      throw new IllegalArgumentException("No user with username: " + quizDto.getOwner());
    }
    QuizEntity quizEntity = quizMapper.mapFrom(quizDto);
    quizEntity.setOwner(userEntity);

    QuizEntity savedQuizEntity = quizRepository.save(quizEntity);

    return quizMapper.mapTo(savedQuizEntity);
  }

  @Override
  public QuestionDto addQuestionToQuiz(String quizId, QuestionDto questionDto) throws JsonProcessingException {
    if (quizRepository.findById(Long.parseLong(quizId)).isPresent()) {
      QuizEntity quizEntity = quizRepository.findById(Long.parseLong(quizId)).get();
      return questionService.createQuestion(quizEntity, questionDto);
    }
    return null;
  }


  /**
   * Finds a page of quizzes in the database.
   *
   * @return A page of quizzes.
   */
  @Override
  public Page<QuizDto> findPageOfQuizzes(Pageable pageable) {
    return quizRepository.findAll(pageable).map(quizMapper::mapTo);
  }

  /**
   * Finds a specified quiz.
   *
   * @param quizId Unique identifier for the quiz.
   * @return A Dto of the queried quiz.
   */
  @Override
  public QuizDto findQuizDtoById(String quizId) {
    Long idValue = Long.parseLong(quizId);
    if (quizRepository.findById(idValue).isPresent()) {
      QuizEntity quizEntity = quizRepository.findById(idValue).get();
      QuizDto foundQuizDto = quizMapper.mapTo(quizEntity);
      return quizMapper.mapTo(quizEntity);
    }
    return null;
  }

  /**
   * Finds the entity of a specified quiz.
   *
   * @param quizId Unique identifier for the quiz to search for.
   * @return An Entity of the queried quiz.
   */
  @Override
  public QuizEntity findQuizEntityById(String quizId) {
    Long idValue = Long.parseLong(quizId);
    if (quizRepository.findById(idValue).isPresent()) {
      return quizRepository.findById(idValue).get();
    }
    return null;
  }

  @Override
  public QuizDto findQuizDetails(String quizId) {
    QuizEntity quizEntity = findQuizEntityById(quizId);
    List<QuestionDto> questions = questionService.getQuestionsByQuiz(quizEntity);
    QuizDto quizDto = quizMapper.mapTo(quizEntity);
    quizDto.setQuestions(questions);
    return quizDto;
  }
}
