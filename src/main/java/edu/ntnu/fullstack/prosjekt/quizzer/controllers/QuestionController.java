package edu.ntnu.fullstack.prosjekt.quizzer.controllers;

import edu.ntnu.fullstack.prosjekt.quizzer.domain.dto.QuestionDto;
import edu.ntnu.fullstack.prosjekt.quizzer.domain.entities.QuestionEntity;
import edu.ntnu.fullstack.prosjekt.quizzer.mappers.Mapper;
import edu.ntnu.fullstack.prosjekt.quizzer.services.QuestionService;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest Controller used for managing requests relating to question database operations.
 * Base endpoint is /api/questions/.
 */
@RequestMapping("/api/questions")
@RestController
@Log
public class QuestionController {
  /**
   * Used for Dependency Injection.
   */
  private QuestionService questionService;

  /**
   * Used for Dependency Injection.
   */
  private Mapper<QuestionEntity, QuestionDto> questionMapper;

  /**
   * Used for Dependency Injection.
   *
   * @param questionService The Injected QuestionService object.
   * @param questionMapper The Injected QuestionMapper object.
   */
  public QuestionController(QuestionService questionService, Mapper<QuestionEntity,
          QuestionDto> questionMapper) {
    this.questionService = questionService;
    this.questionMapper = questionMapper;
  }

  /**
   * Endpoint for creating a question.
   *
   * @param questionDto The question to create.
   * @return A response with a status code and message. Fails if question misses necessary fields.
   */
  @PostMapping("/create")
  public ResponseEntity<?> addQuestion(@RequestBody QuestionDto questionDto) {
    log.info("Received request addQuestion for question: " + questionDto);
    try {
      log.info("Attempting to map questionDto to questionEntity");
      QuestionEntity questionEntity = questionMapper.mapFrom(questionDto);
      log.info("Successfully mapped");
      log.info("Check if necessary fields are set");

      if (questionEntity.getLabel() == null || questionEntity.getLabel().isEmpty()) {
        log.info("Invalid question label");
        return ResponseEntity.badRequest().body("The question must have a label");
      }

      if (questionEntity.getQuiz() == null) {
        log.info("No quiz associated with question");
        return ResponseEntity.badRequest().body("No quiz assigned to question.");
      }

      log.info("Checks finished, sending question to service layer");
      QuestionEntity savedQuestionEntity = questionService.createQuestion(questionEntity);
      log.info("Successfully added question to database: " + questionEntity);
      QuestionDto savedQuestionDto = questionMapper.mapTo(savedQuestionEntity);
      log.info("Sending successful response to frontend");
      return new ResponseEntity<>(savedQuestionDto, HttpStatus.CREATED);
    } catch (Exception e) {
      log.info("An unforeseen error occurred");
      return ResponseEntity.badRequest().body("An unforeseen error occurred");
    }
  }
}
