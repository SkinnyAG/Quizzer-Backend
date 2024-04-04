package edu.ntnu.fullstack.prosjekt.quizzer.controllers;

import edu.ntnu.fullstack.prosjekt.quizzer.domain.dto.QuizDto;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.entities.QuizEntity;
import edu.ntnu.fullstack.prosjekt.quizzer.mappers.Mapper;
import edu.ntnu.fullstack.prosjekt.quizzer.services.QuizService;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Rest Controller for managing requests relating to quiz database operations.
 * Base endpoint is /api/quizzes/.
 */
@RestController
@RequestMapping("/api/quizzes")
@Log
public class QuizController {
  /**
   * Used for Dependency Injection.
   */
  private QuizService quizService;

  /**
   * Used for Dependency Injection.
   */
  private Mapper<QuizEntity, QuizDto> quizMapper;

  /**
   * Used for Dependency Injection.
   *
   * @param quizService The injected QuizService object.
   * @param quizMapper The Injected QuizMapper object.
   */
  public QuizController(QuizService quizService, Mapper<QuizEntity, QuizDto> quizMapper) {
    this.quizService = quizService;
    this.quizMapper = quizMapper;
  }

  /**
   * Endpoint for creating a new quiz.
   *
   * @param quizDto The quiz to be created.
   * @return A response with a status code and message. Fails necessary fields are missing.
   */
  @PostMapping(path = "/create")
  public ResponseEntity<?> createQuiz(@RequestBody QuizDto quizDto) {
    log.info("Request to createQuiz received with quiz: " + quizDto);
    try {

      QuizEntity quizEntity = quizMapper.mapFrom(quizDto);

      if (quizEntity.getTitle() == null || quizEntity.getTitle().isEmpty()) {
        log.info("Missing or illegal quiz title");
        return ResponseEntity.badRequest().body("Invalid quiz name");
      }

      if (quizEntity.getOwner() == null) {
        log.info("Missing or invalid quiz owner");
        return ResponseEntity.badRequest().body("No user associated with quiz");
      }

      QuizEntity savedQuizEntity = quizService.createQuiz(quizEntity);
      log.info("Quiz added to database: " + savedQuizEntity);
      QuizDto savedQuizDto = quizMapper.mapTo(savedQuizEntity);

      return new ResponseEntity<>(savedQuizDto, HttpStatus.CREATED);
    } catch (Exception e) {
      log.info("An unforeseen error occurred");
      return ResponseEntity.badRequest().body("An unforeseen error occurred.");
    }
  }

  @GetMapping(path = "/getPage")
  public ResponseEntity<?> getPageOfQuizzes(Pageable pageable) {
    Page<QuizEntity> quizEntityPage = quizService.findPageOfQuizzes(pageable);
    Page<QuizDto> quizDtoPage = quizEntityPage.map(quizMapper::mapTo);
    return new ResponseEntity<>(quizDtoPage, HttpStatus.OK);
  }
}
