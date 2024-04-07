package edu.ntnu.fullstack.prosjekt.quizzer.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.dto.CategoryDto;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.dto.QuestionDto;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.dto.QuizGeneralDto;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.dto.QuizDetailsDto;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.entities.CategoryEntity;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.entities.QuizEntity;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.entities.UserEntity;
import edu.ntnu.fullstack.prosjekt.quizzer.mappers.Mapper;
import edu.ntnu.fullstack.prosjekt.quizzer.repositories.CategoryRepository;
import edu.ntnu.fullstack.prosjekt.quizzer.repositories.QuizRepository;
import edu.ntnu.fullstack.prosjekt.quizzer.services.QuestionService;
import edu.ntnu.fullstack.prosjekt.quizzer.services.QuizService;
import edu.ntnu.fullstack.prosjekt.quizzer.services.UserService;
import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
  private Mapper<QuizEntity, QuizDetailsDto> quizMapper;

  private Mapper<CategoryEntity, CategoryDto> categoryMapper;

  /**
   * Used for Dependency Injection.
   */
  private UserService userService;

  private QuestionService questionService;

  private CategoryRepository categoryRepository;


  /**
   * Used for Dependency Injection.
   *
   * @param quizRepository The Injected QuizRepository object.
   */
  public QuizServiceImpl(QuizRepository quizRepository, CategoryRepository categoryRepository, UserService userService,
                         QuestionService questionService, Mapper<QuizEntity, QuizDetailsDto> quizMapper,
                         Mapper<CategoryEntity, CategoryDto> categoryMapper) {
    this.quizRepository = quizRepository;
    this.categoryRepository = categoryRepository;
    this.quizMapper = quizMapper;
    this.userService = userService;
    this.questionService = questionService;
    this.categoryMapper = categoryMapper;
  }

  /**
   * Creates a quiz in the database table.
   *
   * @param quizDetailsDto QuizEntity that should be added.
   * @return The created QuizEntity
   */
  @Override
  public QuizDetailsDto createQuiz(QuizDetailsDto quizDetailsDto) {
    log.info("Creating quiz");

    if (quizDetailsDto.getTitle() == null || quizDetailsDto.getTitle().isEmpty()) {
      log.info("Undefined quiz title");
      throw new IllegalArgumentException("Undefined quiz title");
    }

    UserEntity userEntity = userService.findEntityByUsername(quizDetailsDto.getOwner().getUsername());
    if (userEntity == null) {
      log.info("Could not find user");
      throw new IllegalArgumentException("No user with username: " + quizDetailsDto.getOwner());
    }
    QuizEntity quizEntity = quizMapper.mapFrom(quizDetailsDto);
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
  public Page<QuizGeneralDto> findPageOfQuizzes(Pageable pageable) {
    Page<QuizEntity> quizEntityPage = quizRepository.findAll(pageable);

    return quizEntityPage.map(obj -> {
      QuizGeneralDto converted = new ModelMapper().map(obj, QuizGeneralDto.class);
      converted.setAmountOfQuestions(questionService.getAmountOfQuestionsByQuiz(obj));
      return converted;
    });
  }

  /**
   * Finds a specified quiz.
   *
   * @param quizId Unique identifier for the quiz.
   * @return A Dto of the queried quiz.
   */
  @Override
  public QuizDetailsDto findQuizDtoById(String quizId) {
    Long idValue = Long.parseLong(quizId);
    if (quizRepository.findById(idValue).isPresent()) {
      QuizEntity quizEntity = quizRepository.findById(idValue).get();
      QuizDetailsDto foundQuizDetailsDto = quizMapper.mapTo(quizEntity);
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

  @Transactional
  @Override
  public void updateQuizEntity(QuizDetailsDto quizDetailsDto) {
    log.info("Quiz id: " + quizDetailsDto.getQuizId());
    log.info("Quiz title: " + quizDetailsDto.getTitle());
    QuizEntity quizEntity = findQuizEntityById(quizDetailsDto.getQuizId().toString());
    questionService.clearQuestionsByQuizEntity(quizEntity);
    questionService.addListOfQuestions(quizDetailsDto.getQuestions(), quizEntity);
    QuizEntity updatedQuizEntity = new ModelMapper().map(quizDetailsDto, QuizEntity.class);
    quizRepository.save(updatedQuizEntity);
  }

  @Override
  public QuizDetailsDto findQuizDetails(String quizId) {
    QuizEntity quizEntity = findQuizEntityById(quizId);
    List<QuestionDto> questions = questionService.getQuestionsByQuiz(quizEntity);
    QuizDetailsDto quizDetailsDto = quizMapper.mapTo(quizEntity);
    quizDetailsDto.setQuestions(questions);
    return quizDetailsDto;
  }

  @Override
  public Page<QuizGeneralDto> filterQuizzes(String searchQuery, Pageable pageable) {
    Page<QuizEntity> quizEntityPage = quizRepository.findByCategoriesInOrTitleContaining(searchQuery, pageable);
    return quizEntityPage.map(obj -> {
      QuizGeneralDto converted = new ModelMapper().map(obj, QuizGeneralDto.class);
      converted.setAmountOfQuestions(questionService.getAmountOfQuestionsByQuiz(obj));
      return converted;
    });
  }

  public List<CategoryDto> findAllCategories() {
    List<CategoryDto> categories = categoryRepository.findAll().stream().map(categoryEntity -> categoryMapper.mapTo(categoryEntity)).toList();
    return categories;
  }
}
